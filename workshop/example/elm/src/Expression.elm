module Expression exposing (Expression, expression)

import Parser exposing (Parser)
import Utilities as Util


type Expression
    = Constant Int
    | Variable String
    | Addition Expression Expression
    | Subtraction Expression Expression
    | Multiplication Expression Expression
    | Division Expression Expression


expression : Parser Expression
expression =
    let
        compound =
            term
                |> Util.andThen expressionOperator
                |> Util.andThen (Parser.lazy <| \_ -> expression)
                |> Parser.map (\( ( l, o ), r ) -> o l r)
    in
    Util.first <|
        Util.oneOf
            [ compound
            , term
            ]


expressionOperator : Parser (Expression -> Expression -> Expression)
expressionOperator =
    Util.oneOf
        [ Parser.map (\_ -> Addition) <| Parser.character '+'
        , Parser.map (\_ -> Subtraction) <| Parser.character '-'
        ]


term : Parser Expression
term =
    let
        compound =
            factor
                |> Util.andThen termOperator
                |> Util.andThen (Parser.lazy <| \_ -> term)
                |> Parser.map (\( ( l, o ), r ) -> o l r)
    in
    Util.first <|
        Util.oneOf
            [ compound
            , factor
            ]


termOperator : Parser (Expression -> Expression -> Expression)
termOperator =
    Util.oneOf
        [ Parser.map (\_ -> Multiplication) <| Parser.character '*'
        , Parser.map (\_ -> Division) <| Parser.character '/'
        ]


factor : Parser Expression
factor =
    Util.oneOf
        [ constant
        , identifier
        , Util.parenthesized <| Parser.lazy (\_ -> expression)
        ]


constant : Parser Expression
constant =
    Util.greedy1 digit
        |> Parser.map (List.map String.fromChar)
        |> Parser.map (String.join "")
        |> Parser.map (String.toInt >> Maybe.withDefault 0)
        |> Parser.map Constant


digit : Parser Char
digit =
    Parser.satisfy Char.isDigit


identifier : Parser Expression
identifier =
    Util.greedy1 alpha
        |> Parser.map (List.map String.fromChar)
        |> Parser.map (String.join "")
        |> Parser.map Variable


alpha : Parser Char
alpha =
    Parser.satisfy Char.isAlpha
