Parsing is an important tool in a developers toolbox. However, developers often use sub-par mimicries of actual parsers.

In this article we will remedy that and introduce the reader with the idea of parser combinators.

## Part one: a parser
*where our protagonists meet each other behind the keyboard*

- D: Geert, what is a parser actually?
- G: Glad you asked Daan! A parser is a function that transforms text into objects.

- D: So this would be a good signature

```kotlin
(String) -> T
```

- G: Hmmm, almost
- D: What is wrong with it?
- G: Well, what would you return when the you expect a number and the text is "banana"?
- D: Ah, I see. We are missing the possiblity to express a failure. Would this be better?

```kotlin
(String) -> T?
```

- G: Yes, this is a parser... But we can do better!
- D: How?
- G: What if we could combine functions with this "parser signature" in a way that they become bigger and better parsers?
- D: Sounds great! How would we go about this?
- G: Say a text that starts with a number, but ends with " + 12", we would like a next parser to continue where we left of. So we need a way to return this left-over text.
- D: So what about this:

```kotlin
(String) -> Pair<T, String>?
```

- G: Yes! Maybe we should make this official?
- D: Let's do it!

```kotlin
typealias Parser<T> = (String) -> Pair<T, String>?
```

- D: You said something about combining parsers?
- G: I did! But maybe it would be nice if we would implement a few simple parsers? Just to play with the signature and the concept a little bit?
- D: Good idea! What about a parser that parses a single 'A' from a String?
- G: Yes, good one! What would you suggest?
- D: I think the following does implement the Parser signature:

```kotlin
fun parseA(input: String): Pair<Char, String>? =
    if (input.startsWith('A')) {
        'A' to input.drop(1)
    } else {
        null
    }
```

- G: It does! Would it not be great if you can make this more generic?
- D: Do you want to accept the charcter to match? Like so

```kotlin
fun character(needle: Char): Parser<Char> = { input ->
    if (input.startsWith(needle)) {
        needle to input.drop(1)
    } else {
        null
    }
}
```
- G: Yes!
- D: My head hurts a little, and it's late now, lets get some rest and continue tomorrow?
- G: Okay! Goodnight!

## Part two: combining parsers

- G: Hi Daan, brotha from another motha, how did you sleep?
- D: Wazzaap, I was thinking about parsers. You mentioned that it would be nice to combine them. How does that work?

- G: Well, lets say you have a parser for an 'A' and a parser for a 'B'. You want to have a parser that parses either 'A' or 'B'.
- D: That would be nice. Would this work

```kotlin
fun or(p: Parser<Char>, q: Parser<Char>): Parser<Char> = { input ->
    p(input) ?: q(input)
}
```

- G: That is a good first try. But can you improve it?
- D: I am not sure.
- G: Take a look at the type arguments. Now it can only be used with a `Parser<Char>`.
- D: I understand! Let's make it more generic

```kotlin
fun <T> or(p: Parser<T>, q: Parser<T>): Parser<T> = { input ->
    p(input) ?: q(input)
}
```

- G: let's try it out

```kotlin
fun main() {
    val p = or(character('A'), character('B'))
    println(p("A"))
    println(p("B"))
    println(p("C"))
}
```

```plain
('A', "")
('B', "")
null
```

- D: It works!
- G: Whoop whoop!

- G: If we can do `or` we can do `and`.
- D: 🤯
- D: Like this?

```kotlin
fun <S, T> and(p: Parser<S>, q: Parser<T>): Parser<Pair<S, T>> = { input ->
    p(input)?.let { ( first, intermediate) ->
        q(intermediate)?.let { (second, rest) ->
            (first to second) to rest
        }
    }
}
```

- G: Looking good! But the parser returns a pair, what if you want something else?
- D: Can we transform it?
- G: I like how you are thinking! High five!
- D: I will get to it.

```kotlin
fun <S,T> map(p: Parser<S>, transform: (S) -> T): Parser<T> = { input ->
    p(input)?.let { (result, rest) -> transform(result) to rest }
}
```

- G: Let's see if it works

```kotlin
fun main() {
    val p = map(and(character('A'), character('B'))) { (left, right) ->
        "$left$right"
    }
   println(p("ABC"))
}
```

```plain
(AB, C)
```

- D: The first element of the pair is no longer a list, but a String! Just as expected!

- G: What other combinators can you think of?
- D: Let me see... zero or more occurences of 'A'?
- G: That is a good suggestion
- D: Hmm, let me think of an implementation

```kotlin
fun <T> star(p: Parser<T>): Parser<List<T>> = { input ->
    val result = p(input)
    if (result != null) {
        val (match, intermediate) = result
        val (remaining, rest) = star(intermediate)
        (match + remaining) to rest
    } else {
        emptyList() to input
    }
}
```

- D: Would this work?
- G: Lets see

```kotlin
fun main() {
    val p = star(character('A'))
    println(p("AAAAAB"))
}
```

```plain
([A, A, A, A, A], B)
```

- G: It works, but looking at the code it feels a bit ... imperative.
- D: What do you suggest?
- G: We can express the star operation in terms of 'and', 'or' and 'map'
- D: Let me puzzle on this one:

```kotlin
fun <T> star2(p: Parser<T>): Parser<List<T>> =
    or(
        map(and(p, star2(p))) { (r, rs) -> listOf(r) + rs },
        { input -> emptyList<T>() to input }
    )
```

- G: Very nice! You have defined the star operation recursively in terms of itself! This compiles, but will give you an error on runtime. Shall we see?
- D: Yes! Let's go!

```plain
Exception in thread "main" java.lang.StackOverflowError
	at kotlin.jvm.internal.Intrinsics.checkNotNullParameter(Intrinsics.java:130)
	at com.alliander.ptutools.TempJavaMagParserKt.star2(TempJavaMagParser.kt)
	at com.alliander.ptutools.TempJavaMagParserKt.star2(TempJavaMagParser.kt:62)
	at com.alliander.ptutools.TempJavaMagParserKt.star2(TempJavaMagParser.kt:62)
	...
```

- D: Oops!
- G: We need a bigger stack!
- D: No, we should delay the evaluation of the inner star function to when we really need it!
- G: Yeah! What do you suggest?
- D: We can make the evaluation lazy.

```kotlin
fun <T> lazy(parserProducer: () -> Parser<T>): Parser<T> = { input ->
    parserProducer()(input)
}
fun <T> star3(p: Parser<T>): Parser<List<T>> =
    or(
        map(and(p, lazy { star3(p) } )) { (r, rs) -> listOf(r) + rs },
        { input -> emptyList<T>() to input }
    )
```

```kotlin
fun main() {
    val p = star3(character('A'))
    println(p("AAAA"))
}
```

```plain
([A, A, A, A], )
```

- G: Works like a charm! There is one small remark left. The second argument of the `or`, is a function that returns a parser that always succeeds.
- D: We can make it more general! How about `succeed`? And then we can also create a `fail` combinator.

```kotlin
fun <T> succeed(value: T): Parser<T> = { input ->
    value to input
}
fun <T> fail(): Parser<T> = { _ ->
    null
}
```
