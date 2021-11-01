layout: true
class: middle, center


---
# How to implement a parser in 16 minutes or less

---

## Parser

--

Very simple parser that parses an 'A'
```kotlin
fun parseA(input: String): Char
```

--

What if the input does not match?
```kotlin
fun parseA(input: String): List<Char>
```
--

We want to chain parser, so we need the remainder of the input for a next parser:
```kotlin
fun parseA(input: String): List<Pair<Char, String>>
```

--

```kotlin
fun parseA(input: String): List<Pair<Char, String>> =
    if (input.startsWith('A')) {
        listOf('A' to input.drop(1))
    } else {
        emptyList()
    }
```

---

```kotlin
typealias Parser<T> = (String) -> List<Pair<T, String>>
```

--

```kotlin
typealias P<T> = Parser<T>
```

---
layout: true
class: middle, center 

.note[`Parser<T> = (String) -> List<Pair<T, String>>`]

---

```kotlin
fun <T> fail(): Parser<T> = { _ ->
    emptyList()
}
```

---

```kotlin
fun <T> succeed(value: T): Parser<T> = { input ->
    listOf(value to input)
}
```

---

```kotlin
fun satisfy(predicate: (Char) -> Boolean): Parser<Char> = { input ->
    if (input.isNotEmpty() && predicate(input.take(1)) {
        listOf(input.take(1) to input.drop(1))
    } else {
        emptyList()
    }
}
```

---
## Combinators

---

```kotlin
fun <T> or(left: Parser<T>, right: Parser<T>): Parser<T> = { input ->
    left(input) + right(input)
}
```

---

```kotlin
fun <L, R> and(left: Parser<L>, right: Parser<R>): Parser<Pair<L, R>> = { input ->
    left(input).flatMap { (l, intermediate) ->
        right(intermediate).map { (r, remainder) ->
            l to r to remainder
        }
    }
}
```

---

```kotlin
fun <T, U> map(p: Parser<T>, transform: (T) -> U): Parser<U> = { input ->
    p(input)
        .map { (value, remainder) ->
            transform(value) to remainder
        }
}
```

---

```kotlin
fun <T> lazy(producer: () -> Parser<T>): Parser<T> = { input ->
    producer()(input)
}
```

---

```kotlin
fun <T> many(p: Parser<T>): Parser<List<T>> =
    or(
        map(and(p, lazy { many(p) })) { (r, rs) -> listOf(r) + rs},
        succeed(emptyList())
    )
```

---
## Final thoughts

---
