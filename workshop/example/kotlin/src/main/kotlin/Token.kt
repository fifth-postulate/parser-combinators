
fun token(name: String): Parser<String> = { input ->
    if (input.startsWith(name)) {
        listOf(name to input.drop(name.length))
    } else {
        emptyList()
    }
}
