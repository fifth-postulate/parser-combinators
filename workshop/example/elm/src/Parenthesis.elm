module Parenthesis exposing (tree)

import Parser exposing (Parser)
import Utilities as Util


type Tree
    = Leaf
    | Node Tree Tree


tree : Parser Tree
tree =
    Parser.oneOf node leaf


node : Parser Tree
node =
    let
        subTree =
            Parser.lazy (\_ -> tree)
    in
    Util.parenthesized subTree
        |> Util.andThen (Parser.lazy (\_ -> tree))
        |> Parser.map (uncurry Node)


leaf : Parser Tree
leaf =
    Parser.succeed Leaf


curry : (( a, b ) -> c) -> a -> b -> c
curry f a b =
    f ( a, b )


uncurry : (a -> b -> c) -> ( a, b ) -> c
uncurry f ( a, b ) =
    f a b
