layout: true
class: middle, center


---
# How to implement a parser in 16 minutes or less

---
## Parser

--

```kotlin
typealias Parser<T> = (String) -> List<Pair<T, String>>
```

---

```kotlin
fun <T> fail(): Parser<T> = { _ ->
    emptyList()
}
```