

fun character(c: Char): Parser<Char> = { input ->
    if (input.startsWith(c)) {
        listOf(c to input.drop(1))
    } else {
        emptyList()
    }
}

// Implementation of the parseA parser in terms of the character parser. (To avoid naming collision we named it
// different.)
val characterA = character('A')
