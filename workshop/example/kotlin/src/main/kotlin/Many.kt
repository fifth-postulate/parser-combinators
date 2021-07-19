
fun <T> many(p: Parser<T>): Parser<List<T>> =
    or(
        map(and(p, lazy { many(p) })) { (r, rs) -> listOf(r) + rs},
        succeed(emptyList())
    )

fun <T> many1(p: Parser<T>): Parser<List<T>> =
    map(and(p, many(p))) {(r,rs) -> listOf(r) + rs}
