
 val parseA: Parser<Char> = { input ->
    if (input.startsWith('A')) {
        listOf('A' to input.drop(1))
    } else {
        emptyList()
    }
}
