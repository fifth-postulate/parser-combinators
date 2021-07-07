module ProjectList exposing (list)

import Parser exposing (Parser)
import Utilities as Util


list : Parser a -> Parser (List a)
list parser =
    Util.bracketed <| Util.separatedBy comma parser


comma : Parser Char
comma =
    Parser.character ','
