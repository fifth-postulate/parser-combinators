
fun <S, T> and(leftParser: Parser<S>, rightParser: Parser<T>): Parser<Pair<S, T>> = { input ->
    leftParser(input).flatMap { (left, intermediate) ->
        rightParser(intermediate).map { (right, remainder) ->
            left to right to remainder
        }
    }
}
