package utilities

import Parser
import many
import many1

fun <T> greedy(p: Parser<T>): Parser<List<T>> =
    first(many(p)) // Note that this works because many always tries to match the parser before doing a succeed

fun <T> greedy1(p: Parser<T>): Parser<List<T>> =
    first(many1(p))
