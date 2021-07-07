
fun <T, U> map(p: Parser<T>, transform: (T) -> U): Parser<U> = { input ->
    p(input)
        .map { (value, remainder) ->
            transform(value) to remainder
        }
}
