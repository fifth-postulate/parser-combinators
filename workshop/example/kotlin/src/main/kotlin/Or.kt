
fun <T> or(left: Parser<T>, right: Parser<T>): Parser<T> = { input ->
    left(input) + right(input)
}
