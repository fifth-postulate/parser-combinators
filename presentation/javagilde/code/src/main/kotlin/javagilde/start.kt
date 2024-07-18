package javagilde.start

sealed class Expression {
    abstract fun evaluate(): Int
}

data class Multiplication(val left: Expression, val right: Expression): Expression() {
    override fun evaluate(): Int =
        left.evaluate() * right.evaluate()
}

data class Addition(val left: Expression, val right: Expression) : Expression() {
    override fun evaluate(): Int =
        left.evaluate() + right.evaluate()
}

data class Constant(val value: Int): Expression() {
    override fun evaluate(): Int =
        value
}


fun main() {
    val expr = Addition(Multiplication(Constant(5), Constant(2)), Constant(1))
    println(expr)
    println(expr!!.evaluate())
}

