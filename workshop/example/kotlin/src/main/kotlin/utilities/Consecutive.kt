package utilities

import Parser
import andThen
import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import map
import succeed

fun consecutive(vararg ps: Parser<*>): Parser<List<Any?>> =
    ps.fold(succeed(emptyList())) { a, b ->
        map(andThen(a, b)) { (rs, r) ->
            rs + listOf(r)
        }
    }

// Note that the above definition does not retain any type information we had. This is not very useful, so we will
// define a set of consecutive parsers that introduce some noise, but are useful. We use the Tuple* types of Arrow-Kt.

fun <A, B, C> consecutive(p1: Parser<A>, p2: Parser<B>, p3: Parser<C>): Parser<Triple<A, B, C>> =
    map(andThen(andThen(p1, p2), p3)) { (ab, c) ->
        val (a, b) = ab
        Triple(a, b, c)
    }

fun <A, B, C, D> consecutive(p1: Parser<A>, p2: Parser<B>, p3: Parser<C>, p4: Parser<D>): Parser<Tuple4<A, B, C, D>> =
    map(andThen(consecutive(p1, p2, p3), p4)) { (abc, d) ->
        val (a, b, c) = abc
        Tuple4(a, b, c, d)
    }

fun <A, B, C, D, E> consecutive(
    p1: Parser<A>,
    p2: Parser<B>,
    p3: Parser<C>,
    p4: Parser<D>,
    p5: Parser<E>
): Parser<Tuple5<A, B, C, D, E>> =
    map(andThen(consecutive(p1, p2, p3, p4), p5)) { (abcd, e) ->
        val (a, b, c, d) = abcd
        Tuple5(a, b, c, d, e)
    }

fun <A, B, C, D, E, F> consecutive(
    p1: Parser<A>,
    p2: Parser<B>,
    p3: Parser<C>,
    p4: Parser<D>,
    p5: Parser<E>,
    p6: Parser<F>
): Parser<Tuple6<A, B, C, D, E, F>> =
    map(andThen(consecutive(p1, p2, p3, p4, p5), p6)) { (abcde, f) ->
        val (a, b, c, d, e) = abcde
        Tuple6(a, b, c, d, e, f)
    }

// And so on...