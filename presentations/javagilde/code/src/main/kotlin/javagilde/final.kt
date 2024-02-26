package javagilde


// We willen expressies parsen, d.w.z 5*2 + 1
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

typealias Parser<T> = (String) -> List<Pair<T, String>>

fun parserA(input: String): List<Pair<Char, String>> {
    return if (input.startsWith('A')) {
        listOf('A' to input.drop(1))
    } else {
        emptyList()
    }}

fun character(target: Char): Parser<Char> = { input: String ->
        if (input.startsWith(target)) {
            listOf( target to input.drop(1))
        } else {
            emptyList()
        }
    }

//val parserA = character('A')

fun satisfy(predicate: (Char) -> Boolean): Parser<Char> = { input: String ->
        if (input.length > 0 && predicate(input[0])) {
                listOf(input[0] to input.drop(1))
        } else {
            emptyList()
        }
    }

val parserAorB = satisfy { it == 'A' || it == 'B'}

fun <T> or(p: Parser<T>, q: Parser<T>): Parser<T> = { input ->
    // How to implement?
    p(input) + q(input)
}

infix fun <T> Parser<T>.`|||`(p: Parser<T>): Parser<T> =
    or(this, p)

fun <S, T> and(p: Parser<S>, q: Parser<T>): Parser<Pair<S,T>> = {input ->
    p(input)
        .flatMap { (s, intermediate) ->
            q(intermediate).map { (t, rest) -> (s to t) to rest }
        }
}

infix fun <S, T> Parser<S>.`|&|`(p: Parser<T>): Parser<Pair<S, T>> =
    and(this, p)

fun <S, T> map(p: Parser<S>, transform: (S) -> T): Parser<T> = { input ->
    p(input)
        .map {(s, rest) -> transform(s) to rest }
}

infix fun <S, T> Parser<S>.`&|`(p: Parser<T>): Parser<T> =
    map(and(this, p)) {(_, t) -> t}

infix fun <S, T> Parser<S>.`|&`(p: Parser<T>): Parser<S> =
    map(and(this, p)) {(s, _) -> s}

fun <T> succeed(result: T): Parser<T> = {input ->
    listOf( result to input )
}

fun <T> lazy(p: () -> Parser<T>): Parser<T> = { input ->
    p()(input)
}

fun <T> many(p: Parser<T>): Parser<List<T>> =
    or(
        map(and(p, lazy {many(p)})) { (r, rs) -> listOf(r) + rs},
        succeed(emptyList())
    )

fun <T> many1(p: Parser<T>): Parser<List<T>> =
    map(p `|&|` many(p)) {(r,rs) -> listOf(r) + rs}

val digit: Parser<Int> =
    map(satisfy { c -> c in '0' .. '9'}) { it - '0' }

val number: Parser<Int> = map(many1(digit)) {
    it.fold(0) {acc, d -> 10*acc + d}
}

val constant: Parser<Expression> =
    map(number) {n -> Constant(n)}

fun multiplication(): Parser<Expression> =
    constant `|||` map(constant `|&` character('*') `|&|` lazy(::multiplication)) {
        (left, right) -> Multiplication(left, right)
    }

fun addition(): Parser<Expression> =
    multiplication() `|||` map(multiplication() `|&` character('+') `|&|` lazy(::addition)) {
        (left, right) -> Addition(left, right)
    }

fun expression() : Parser<Expression> =
    addition()

fun <T> just(p: Parser<T>): Parser<T> = {input ->
    p(input)
        .filter {(_, rest) -> rest.isEmpty() }
}

fun <T> parse(parser: Parser<T>, input: String): T? =
    just(parser)(input).get(0).first

fun main() {
    // 5*2 + 1
    val expr = parse(expression(), "5*2+1")//Addition(Multiplication(Constant(5), Constant(2)), Constant(1))
    println(expr)
    println(expr!!.evaluate())
}

