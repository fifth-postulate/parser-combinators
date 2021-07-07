module Template exposing (Template, evaluate, template)

import Dict exposing (Dict)
import Parser exposing (Parser)
import Utilities as Util


type Template
    = Literal String
    | Substitution String
    | Concatenation (List Template)


type alias Context =
    Dict String String


evaluate : Context -> Template -> String
evaluate context t =
    case t of
        Literal l ->
            l

        Substitution name ->
            Dict.get name context
                |> Maybe.withDefault ""

        Concatenation templates ->
            templates
                |> List.map (evaluate context)
                |> String.join ""


template : Parser Template
template =
    let
        parser =
            Parser.oneOf literal substitution
    in
    Util.greedy parser
        |> Parser.map Concatenation


literal : Parser Template
literal =
    Util.oneOf
        [ Parser.token "\\\\" |> Parser.map (\_ -> "\\")
        , Parser.token "\\{" |> Parser.map (\_ -> "{")
        , allowed
        ]
        |> Parser.many1
        |> Parser.map (\result -> String.join "" result)
        |> Parser.map Literal


allowed : Parser String
allowed =
    Parser.satisfy (\c -> not (c == '{' || c == '\\'))
        |> Parser.map String.fromChar


substitution : Parser Template
substitution =
    Util.braced identifier
        |> Parser.map Substitution


identifier : Parser String
identifier =
    Parser.many1 alpha
        |> Parser.map (List.map String.fromChar)
        |> Parser.map (String.join "")


alpha : Parser Char
alpha =
    Parser.satisfy Char.isAlpha
