package utilities

import Parser
import satisfy

val whitespace: Parser<Char> = satisfy { a -> a.isWhitespace() }
