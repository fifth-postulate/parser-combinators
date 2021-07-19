module Parser exposing (..)


type alias Parser a =
    String -> List ( a, String )


succeed : a -> Parser a
succeed value input =
    [ ( value, input ) ]


fail : Parser a
fail _ =
    []


satisfy : (Char -> Bool) -> Parser Char
satisfy predicate input =
    case String.uncons input of
        Just ( c, rest ) ->
            if predicate c then
                [ ( c, rest ) ]

            else
                []

        Nothing ->
            []


character : Char -> Parser Char
character c =
    satisfy ((==) c)


token : String -> Parser String
token target input =
    if String.startsWith target input then
        [ ( target, String.dropLeft (String.length target) input ) ]

    else
        []


or : Parser a -> Parser a -> Parser a
or left right input =
    List.concat [ left input, right input ]


and : Parser a -> Parser b -> Parser ( a, b )
and first second input =
    let
        andThenSecond ( a, intermediate ) =
            second intermediate
                |> List.map (lift (Tuple.pair a))
    in
    first input
        |> List.concatMap andThenSecond


lift : (a -> b) -> ( a, String ) -> ( b, String )
lift f ( a, rest ) =
    ( f a, rest )


just : Parser a -> Parser a
just parser input =
    let
        nothingRemaining ( _, rest ) =
            rest == ""
    in
    parser input
        |> List.filter nothingRemaining


map : (a -> b) -> Parser a -> Parser b
map transform parser input =
    parser input
        |> List.map (lift transform)


lazy : (() -> Parser a) -> Parser a
lazy producer input =
    producer () <| input


many : Parser a -> Parser (List a)
many parser =
    let
        combine ( head, tail ) =
            head :: tail
    in
    or (map combine <| and parser <| lazy (\_ -> many parser)) (succeed [])


many1 : Parser a -> Parser (List a)
many1 parser =
    let
        combine ( head, tail ) =
            head :: tail
    in
    map combine <| and parser <| many parser
