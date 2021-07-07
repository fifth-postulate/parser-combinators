
// Note that in kotlin we cannot just do `fun <T> lazy(producer: () -> Parser<T>): Parser<T> = producer()` since that
// would immediately return the parser making it not lazy. Try to uncomment out the line below and run the test to see
// how this works.
fun <T> lazy(producer: () -> Parser<T>): Parser<T> = { input ->
    producer()(input)
}

// fun <T> lazy(producer: () -> Parser<T>): Parser<T> = producer()
