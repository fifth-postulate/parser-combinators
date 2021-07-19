module ParserTest exposing (..)

import Expect exposing (Expectation)
import Fuzz exposing (Fuzzer, char, constant, int, intRange, list, string, tuple)
import Parser exposing (..)
import Test exposing (Test, describe, fuzz, fuzz2, fuzz3, test)


suite : Test
suite =
    describe "Parser"
        [ describe "succeed"
            [ fuzz2 int string "should return constant value and original input" <|
                \c input ->
                    let
                        parser =
                            succeed c

                        actual =
                            parser input

                        expected =
                            [ ( c, input ) ]
                    in
                    Expect.equal actual expected
            ]
        , describe "fail"
            [ fuzz string "should always return empty list" <|
                \input ->
                    let
                        parser =
                            fail

                        actual =
                            parser input

                        expected =
                            []
                    in
                    Expect.equal actual expected
            ]
        , describe "character"
            [ fuzz2 char string "should match the first character" <|
                \c rest ->
                    let
                        parser =
                            character c

                        input =
                            String.cons c rest

                        actual =
                            parser input

                        expected =
                            [ ( c, rest ) ]
                    in
                    Expect.equal actual expected
            , fuzz3 char char string "should match the character if it equals the target" <|
                \target c rest ->
                    let
                        parser =
                            character target

                        input =
                            String.cons c rest

                        actual =
                            parser input

                        expected =
                            if target == c then
                                [ ( target, rest ) ]

                            else
                                []
                    in
                    Expect.equal actual expected
            ]
        , describe "satisfy"
            [ fuzz3 char char string "with equals predicate should match character" <|
                \target c rest ->
                    let
                        parser =
                            satisfy ((==) target)

                        input =
                            String.cons c rest

                        actual =
                            parser input

                        expected =
                            character target <| input
                    in
                    Expect.equal actual expected
            ]
        , describe "or"
            [ test "matches left alternative" <|
                \_ ->
                    let
                        parser =
                            or (character 'A') (character 'B')

                        actual =
                            parser "ABCD"

                        expected =
                            [ ( 'A', "BCD" ) ]
                    in
                    Expect.equal actual expected
            , test "matches right alternative" <|
                \_ ->
                    let
                        parser =
                            or (character 'A') (character 'B')

                        actual =
                            parser "BACD"

                        expected =
                            [ ( 'B', "ACD" ) ]
                    in
                    Expect.equal actual expected
            ]
        , describe "and"
            [ fuzz3 char char string "matches pair" <|
                \f s rest ->
                    let
                        parser =
                            and (character f) (character s)

                        input =
                            rest
                                |> String.cons s
                                |> String.cons f

                        actual =
                            parser input

                        expected =
                            [ ( ( f, s ), rest ) ]
                    in
                    Expect.equal actual expected
            ]
        , describe "just"
            [ fuzz char "matches when nothing remains" <|
                \c ->
                    let
                        parser =
                            character c

                        actual =
                            parser <| String.fromChar c

                        expected =
                            [ ( c, "" ) ]
                    in
                    Expect.equal actual expected
            , fuzz2 char string "does not match when something remains" <|
                \c rest ->
                    let
                        parser =
                            just <| character c

                        input =
                            String.cons c rest

                        actual =
                            parser input

                        expected =
                            if rest == "" then
                                [ ( c, "" ) ]

                            else
                                []
                    in
                    Expect.equal actual expected
            ]
        , describe "map"
            [ fuzz3 char char string "transforms a successful result" <|
                \target c rest ->
                    let
                        parser =
                            map (always target) (character c)

                        input =
                            String.cons c rest

                        actual =
                            parser input

                        expected =
                            [ ( target, rest ) ]
                    in
                    Expect.equal actual expected
            ]
        , describe "lazy"
            [ fuzz3 char char string "should produce a parser" <|
                \target c rest ->
                    let
                        parser =
                            lazy <| \_ -> character target

                        input =
                            String.cons c rest

                        actual =
                            parser input

                        expected =
                            if target == c then
                                [ ( target, rest ) ]

                            else
                                []
                    in
                    Expect.equal actual expected
            ]
        , describe "many"
            [ fuzz (intRange 0 10) "should parse any number of 'A'" <|
                \n ->
                    let
                        result =
                            List.repeat n 'A'

                        input =
                            result
                                |> List.map String.fromChar
                                |> String.join ""

                        parser =
                            many <| character 'A'

                        actual =
                            parser input
                    in
                    Expect.false "expected a result" <| List.isEmpty actual
            ]
        ]
