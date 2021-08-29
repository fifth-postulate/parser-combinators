package projects

import Parser
import character
import or
import utilities.bracketed

fun <T> list(p: Parser<T>): Parser<T> = bracketed(p)

val parserAsOrBs = or(list(character('A')), list(character('B')))
val parserAsAndBs = list(or(character('A'), list(character('B'))))
