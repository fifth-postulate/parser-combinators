module Parser exposing (..)


parse : Parser Char a -> String -> List ( String, a )
parse p input =
    input
        |> String.toList
        |> p
        |> List.map (Tuple.mapFirst String.fromList)


type alias Parser a b =
    List a -> List ( List a, b )


satisfy : (s -> Bool) -> Parser s s
satisfy predicate input =
    case input of
        x :: xs ->
            if predicate x then
                [ ( xs, x ) ]

            else
                []

        [] ->
            []


symbol : s -> Parser s s
symbol a =
    satisfy (\x -> x == a)


token : List s -> Parser s (List s)
token k input =
    let
        n =
            List.length input
    in
    if k == List.take n input then
        [ ( List.drop n input, k ) ]

    else
        []


succeed : r -> Parser s r
succeed v input =
    [ ( input, v ) ]


epsilon : Parser a ()
epsilon =
    succeed ()


fail : Parser s r
fail _ =
    []


and : Parser s a -> Parser s b -> Parser s ( a, b )
and p1 p2 input =
    let
        continueWith ( xs, v1 ) =
            xs
                |> p2
                |> List.map (Tuple.mapSecond (\v2 -> ( v1, v2 )))
    in
    input
        |> p1
        |> List.concatMap continueWith


land : Parser s a -> Parser s b -> Parser s ( b, a )
land p2 p1 =
    and p1 p2


or : Parser s a -> Parser s a -> Parser s a
or p1 p2 input =
    p1 input ++ p2 input


sp : Parser Char a -> Parser Char a
sp p =
    p << dropWhile (\c -> c == ' ')


dropWhile : (Char -> Bool) -> List Char -> List Char
dropWhile predicate characters =
    case characters of
        c :: cs ->
            if predicate c then
                dropWhile predicate cs

            else
                c :: cs

        [] ->
            []


just : Parser s a -> Parser s a
just p =
    List.filter (List.isEmpty << Tuple.first) << p


map : (a -> b) -> Parser s a -> Parser s b
map transform p =
    List.map (Tuple.mapSecond transform) << p


digit : Parser Char Int
digit =
    let
        ord c =
            Char.toCode c - Char.toCode '0'
    in
    satisfy Char.isDigit
        |> map ord


type alias DeterministicParser symbol result =
    List symbol -> Maybe result


some : Parser s a -> DeterministicParser s a
some p =
    Maybe.map Tuple.second << List.head << just p


type BinaryTree
    = Nil
    | Binary BinaryTree BinaryTree


lazy : (() -> Parser a b) -> Parser a b
lazy p input =
    input
        |> p ()


keepLeft : Parser s b -> Parser s a -> Parser s a
keepLeft p2 p1 =
    and p1 p2
        |> map Tuple.first


keepRight : Parser s b -> Parser s a -> Parser s b
keepRight p2 p1 =
    and p1 p2
        |> map Tuple.second


open : Parser Char Char
open =
    symbol '('


close : Parser Char Char
close =
    symbol ')'


foldParenthesis : (( a, a ) -> a) -> a -> Parser Char a
foldParenthesis toTree initial =
    let
        lazyFoldParenthesis : Parser Char a
        lazyFoldParenthesis =
            lazy (\_ -> foldParenthesis toTree initial)
    in
    or
        (open
            |> keepRight lazyFoldParenthesis
            |> keepLeft close
            |> land lazyFoldParenthesis
            |> map toTree
        )
        (succeed initial)


parenthesis : Parser Char BinaryTree
parenthesis =
    foldParenthesis (uncurry Binary) Nil


uncurry : (a -> b -> c) -> ( a, b ) -> c
uncurry f tuple =
    f (Tuple.first tuple) (Tuple.second tuple)


nesting : Parser Char Int
nesting =
    foldParenthesis (\( x, y ) -> max (1 + x) y) 0


many : Parser s a -> Parser s (List a)
many p =
    let
        lazyManyP =
            lazy (\_ -> many p)
    in
    or (map list <| and p lazyManyP)
        (succeed [])


list : ( a, List a ) -> List a
list ( x, xs ) =
    x :: xs


natural : Parser Char Int
natural =
    let
        f b a =
            10 * a + b
    in
    many digit
        |> map (List.foldl f 0)


many1 : Parser s a -> Parser s (List a)
many1 p =
    p
        |> land (many p)
        |> map list


option : Parser s a -> Parser s (Maybe a)
option p =
    or (map Just p)
        (succeed Nothing)


pack : Parser s a -> Parser s b -> Parser s c -> Parser s b
pack s1 p s2 =
    s1
        |> keepRight p
        |> keepLeft s2


parenthesized : Parser Char a -> Parser Char a
parenthesized p =
    pack (symbol '(') p (symbol ')')


bracketed : Parser Char a -> Parser Char a
bracketed p =
    pack (symbol '[') p (symbol ']')


compound : Parser Char a -> Parser Char a
compound p =
    pack (token <| String.toList "begin") p (token <| String.toList "end")


listOf : Parser s a -> Parser s b -> Parser s (List a)
listOf p s =
    or
        (p
            |> land (many (s |> keepRight p))
            |> map list
        )
        (succeed [])


commaList : Parser Char a -> Parser Char (List a)
commaList p =
    listOf p <| symbol ','


semicolonList : Parser Char a -> Parser Char (List a)
semicolonList p =
    listOf p <| symbol ';'


sequence : List (Parser s a) -> Parser s (List a)
sequence parsers =
    case parsers of
        p :: ps ->
            p
                |> land (sequence ps)
                |> map list

        [] ->
            succeed []


choice : List (Parser s a) -> Parser s a
choice parsers =
    case parsers of
        p :: ps ->
            or p
                (choice ps)

        [] ->
            fail


token2 : List s -> Parser s (List s)
token2 ss =
    ss
        |> List.map symbol
        |> sequence


chain : ((( a -> a -> a, a ) -> a -> a) -> a -> List ( a -> a -> a, a ) -> a) -> Parser s a -> Parser s (a -> a -> a) -> Parser s a
chain fold p s =
    let
        apply : ( a -> a -> a, a ) -> a -> a
        apply ( op, y ) x =
            op x y

        transform : ( a, List ( a -> a -> a, a ) ) -> a
        transform ( initial, xs ) =
            fold apply initial xs
    in
    p
        |> land (many <| and s p)
        |> map transform


chainl : Parser s a -> Parser s (a -> a -> a) -> Parser s a
chainl =
    chain List.foldl


chainr : Parser s a -> Parser s (a -> a -> a) -> Parser s a
chainr =
    chain List.foldr


optionMap : (a -> b) -> b -> Parser s (Maybe a) -> Parser s b
optionMap transform default =
    map (Maybe.map transform >> Maybe.withDefault default)


fract : Parser Char Float
fract =
    let
        f d acc =
            (acc + toFloat d) / 10.0
    in
    many digit
        |> map (List.foldr f 0.0)


fixed : Parser Char Float
fixed =
    let
        decimal =
            symbol '.'
                |> keepRight fract
    in
    map toFloat natural
        |> land (optionMap identity 0.0 <| option decimal)
        |> map (uncurry (+))


integer : Parser Char Int
integer =
    (optionMap (\_ -> (*) -1) identity <| option (symbol '-'))
        |> land natural
        |> map (\( f, v ) -> f v)


first : Parser s a -> Parser s a
first p input =
    p input
        |> List.head
        |> Maybe.map List.singleton
        |> Maybe.withDefault []


greedy : Parser s a -> Parser s (List a)
greedy =
    first << many


greedy1 : Parser s a -> Parser s (List a)
greedy1 =
    first << many1


compulsion : Parser s a -> Parser s (Maybe a)
compulsion =
    first << option


type ArithmeticExpression
    = Constant Int
    | Variable String
    | FunctionCall String (List ArithmeticExpression)
    | Plus ArithmeticExpression ArithmeticExpression
    | Minus ArithmeticExpression ArithmeticExpression
    | Times ArithmeticExpression ArithmeticExpression
    | Divide ArithmeticExpression ArithmeticExpression


identifier : Parser Char String
identifier =
    greedy1 (satisfy Char.isAlpha)
        |> map String.fromList


arithmeticExpression : Parser Char ArithmeticExpression
arithmeticExpression =
    chainr term <|
        or (symbol '+' |> map (\_ -> Plus))
            (symbol '-' |> map (\_ -> Minus))


term : Parser Char ArithmeticExpression
term =
    chainr factor <|
        or (symbol '*' |> map (\_ -> Times))
            (symbol '/' |> map (\_ -> Divide))


factor : Parser Char ArithmeticExpression
factor =
    let
        identifiedExpression ( id, arguments ) =
            arguments
                |> Maybe.map (FunctionCall id)
                |> Maybe.withDefault (Variable id)
    in
    choice
        [ map Constant integer
        , identifier
            |> land (option <| parenthesized (commaList <| lazy (\_ -> arithmeticExpression)))
            |> map identifiedExpression
        , parenthesized <| lazy (\_ -> arithmeticExpression)
        ]


type alias Operation a =
    ( Char, a -> a -> a )


generate : List (Operation a) -> Parser Char a -> Parser Char a
generate operations p =
    let
        operation ( c, o ) =
            symbol c |> map (\_ -> o)

        ops =
            operations
                |> List.map operation
    in
    chainr p <|
        choice ops


multis : List (Operation ArithmeticExpression)
multis =
    [ ( '*', Times ), ( '/', Divide ) ]


addis : List (Operation ArithmeticExpression)
addis =
    [ ( '+', Plus ), ( '/', Minus ) ]


expr : Parser Char ArithmeticExpression
expr =
    List.foldr generate factor [ addis, multis ]


type alias Environment a b =
    List ( a, b )


association : a -> Environment a b -> Maybe b
association key environment =
    case environment of
        ( u, v ) :: xs ->
            if key == u then
                Just v

            else
                association key xs

        [] ->
            Nothing


mapEnvironment : (a -> b) -> Environment s a -> Environment s b
mapEnvironment f environment =
    List.map (Tuple.mapSecond f) environment


type Symbol
    = Terminal String
    | NonTerminal String


type alias Alternative =
    List Symbol


type alias RightHandSide =
    List Alternative


type alias Grammar =
    Environment Symbol RightHandSide


bnf : Parser Char String -> Parser Char String -> Parser Char Grammar
bnf nonTerminalP terminalP =
    let
        rule =
            nonTerminal
                |> land
                    (spToken (String.toList "::=")
                        |> keepRight rhs
                        |> keepLeft (spSymbol '.')
                    )

        rhs =
            listOf alt (spSymbol '|')

        alt =
            many (or terminal nonTerminal)

        terminal =
            map Terminal <| sp terminalP

        nonTerminal =
            map NonTerminal <| sp nonTerminalP
    in
    many rule


spToken : List Char -> Parser Char (List Char)
spToken ps =
    sp <| token ps


spSymbol : Char -> Parser Char Char
spSymbol c =
    sp <| symbol c


type Tree
    = Node Symbol (List Tree)


parseGrammar : Grammar -> Symbol -> Parser Symbol Tree
parseGrammar grammar start =
    let
        parseSymbol : Symbol -> Parser Symbol Tree
        parseSymbol s =
            case s of
                Terminal t ->
                    symbol s
                        |> map (always [])
                        |> map (Node s)

                NonTerminal n ->
                    grammar
                        |> association s
                        |> Maybe.map parseRightHandSide
                        |> Maybe.map (map (Node s))
                        |> Maybe.withDefault fail

        parseRightHandSide : RightHandSide -> Parser Symbol (List Tree)
        parseRightHandSide rhs =
            rhs
                |> List.map parseAlternative
                |> choice

        parseAlternative : Alternative -> Parser Symbol (List Tree)
        parseAlternative alternatives =
            alternatives
                |> List.map (\s -> lazy (\_ -> parseSymbol s))
                |> sequence
    in
    parseSymbol start


type alias SymbolSet =
    Parser Char String


type ContextFreeGrammar
    = ContextFreeGrammar SymbolSet SymbolSet String Symbol


parserGenerator : ContextFreeGrammar -> Maybe (Parser Symbol Tree)
parserGenerator (ContextFreeGrammar nonTerminalP terminalP bnfString start) =
    let
        parser =
            bnf nonTerminalP terminalP
                |> map parseGrammar
                |> some
    in
    bnfString
    |> String.toList
    |> parser
    |> Maybe.map (\p -> p start)

twopass : Parser a b -> Parser b c -> Parser a c
twopass lexer parser input =
    let
        parseTokens (rest, tokens) =
            tokens
            |> parser
            |> List.map (Tuple.mapFirst (always rest))
    in
    input
    |> many lexer
    |> List.concatMap parseTokens
