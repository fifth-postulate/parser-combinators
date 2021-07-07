
fun <T> succeed(value: T): Parser<T> = { input ->
    listOf(value to input)
}

fun <T> fail(): Parser<T> = { _ ->
    emptyList()
}
