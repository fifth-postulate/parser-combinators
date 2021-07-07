
fun <T> many(p: Parser<T>): Parser<List<T>> =
    oneOf(
        map(andThen(p, lazy { many(p) })) { (r, rs) -> listOf(r) + rs},
        succeed(emptyList())
    )

fun <T> many1(p: Parser<T>): Parser<List<T>> =
    map(andThen(p, many(p))) {(r,rs) -> listOf(r) + rs}
