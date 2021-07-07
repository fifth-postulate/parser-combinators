package utilities

import Parser

fun <T> first(p: Parser<T>): Parser<T> = { input ->
    p(input).take(1)
}
