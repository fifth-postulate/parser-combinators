package utilities

import Parser
import map

fun <T> ignore(p: Parser<T>): Parser<Unit> =
    map(p) { } // Map to Unit
