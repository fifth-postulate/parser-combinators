
fun satisfy(predicate: (Char) -> Boolean): Parser<Char> = { input ->
    if (input.isNotEmpty() && predicate(input[0])) {
        listOf(input[0] to input.drop(1))
    } else {
        emptyList()
    }
}
