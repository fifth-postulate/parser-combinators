
fun <T> just(p: Parser<T>): Parser<T> = { input ->
    p(input)
        .filter { (_, remainder) ->
            remainder.isEmpty()
        }
}
