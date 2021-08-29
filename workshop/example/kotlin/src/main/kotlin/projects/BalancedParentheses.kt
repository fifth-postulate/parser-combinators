package projects

import Parser
import character
import lazy
import map
import or
import succeed
import utilities.consecutive

sealed class Tree<T>
data class Leaf<T>(val x: T) : Tree<T>()
data class Node<T>(val left: Tree<T>, val right: Tree<T>): Tree<T>()

fun <T> genericParentheses(left: Char, p: Parser<T>, right: Char): Parser<Tree<T>> =
    or(
        map(
            consecutive(
                character(left),
                lazy { genericParentheses(left, p, right) },
                character(right),
                lazy { genericParentheses(left, p, right) },
            )
        ) { (_, left, _, right) ->
            Node(left, right)
        },
        map(p) { Leaf(it) }
    )

val epsilon: Parser<Unit> = succeed(Unit)
fun parentheses(left: Char, right: Char) = genericParentheses(left, epsilon, right)

