package utilities

import Parser
import character
import map

fun <L, T, R> pack(left: Parser<L>, p: Parser<T>, right: Parser<R>): Parser<T> =
    map(consecutive(left, p, right)) { (_, t, _) ->
        t
    }

fun <T> parenthesized(p: Parser<T>) = pack(character('('), p, character(')'))

fun <T> bracketed(p: Parser<T>) = pack(character('['), p, character(']'))
