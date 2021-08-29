package utilities

import Parser
import and
import many
import map

fun <S, T> separatedBy(separator: Parser<S>, p: Parser<T>): Parser<List<T>> =
    map(
        and(
            many(
                map(
                    and(p, separator)
                ) { (t, s) ->
                    t
                }
            ),
            p
        )
    ) { (listT, t) ->
        listT + t
    }
