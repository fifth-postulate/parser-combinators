package utilities

import Parser
import and
import many
import map

fun <T> sp(p: Parser<T>): Parser<T> =
    map(and(many(whitespace), p)) { (_, t) ->
        t
    }
