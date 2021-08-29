package utilities

import Parser
import or

fun <T> oneOf(vararg ps: Parser<T>): Parser<T> =
    ps.reduce { a, b ->
        or(a, b)
    }
