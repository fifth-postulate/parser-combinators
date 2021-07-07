module Utilities exposing (..)

import Parser exposing (Parser)


ignore : Parser a -> Parser ()
ignore =
    Parser.map (always ())


first : Parser a -> Parser a
first parser input =
    parser input
        |> List.take 1


greedy : Parser a -> Parser (List a)
greedy =
    first << Parser.many


greedy1 : Parser a -> Parser (List a)
greedy1 =
    first << Parser.many1


consecutive : List (Parser a) -> Parser (List a)
consecutive parsers =
    case parsers of
        [] ->
            Parser.succeed []

        p :: ps ->
            let
                combine ( x, xs ) =
                    x :: xs
            in
            Parser.map combine <| Parser.andThen p <| consecutive ps


oneOf : List (Parser a) -> Parser a
oneOf parsers =
    case parsers of
        [] ->
            Parser.fail

        p :: ps ->
            Parser.oneOf p <| oneOf ps


whitespace : Parser Char
whitespace =
    oneOf
        [ Parser.character ' '
        , Parser.character '\t'
        , Parser.character '\n'
        ]


sp : Parser a -> Parser a
sp parser =
    Parser.many whitespace
        |> keepRight parser


keepRight : Parser b -> Parser a -> Parser b
keepRight right left =
    Parser.map Tuple.second <| Parser.andThen left right


keepLeft : Parser b -> Parser a -> Parser a
keepLeft right left =
    Parser.map Tuple.first <| Parser.andThen left right


andThen : Parser b -> Parser a -> Parser ( a, b )
andThen right left =
    Parser.andThen left right


pack : Parser a -> Parser b -> Parser c -> Parser b
pack left main right =
    left
        |> keepRight main
        |> keepLeft right


delimited : Char -> Char -> Parser a -> Parser a
delimited opening closing parser =
    pack (Parser.character opening) parser (Parser.character closing)


parenthesized : Parser a -> Parser a
parenthesized =
    delimited '(' ')'


bracketed : Parser a -> Parser a
bracketed =
    delimited '[' ']'


braced : Parser a -> Parser a
braced =
    delimited '{' '}'


separatedBy : Parser a -> Parser b -> Parser (List b)
separatedBy separator parser =
    let
        multiple =
            parser
                |> keepLeft separator
                |> andThen (Parser.lazy <| \_ -> separatedBy separator parser)
                |> Parser.map (\( x, xs ) -> x :: xs)

        single =
            parser
                |> Parser.map List.singleton
    in
    Parser.oneOf multiple single
