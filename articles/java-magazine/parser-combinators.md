On the interface of systems data often gets transformed from one form to the other. With the ubiquitous data-format JSON this is often unnoticed. One just includes a library and maybe set some annotations and be done.

You're not always that lucky. Sometimes it is necessary to handle data in an unconventional, or ad-hoc, format. Without the crutch of a library to rely on developers need to express in code how to transform one kind of data into an in-memory representation of the model the data represents. This is called parsing and it is an important tool in a developer's toolbox. However, developers often use sub-par mimicries of actual parsers that barely do the job.

In this play, we will remedy that and introduce the reader to the idea of **parser combinators**. It is best performed in an open office plan to help the germination and spread of the ideas presented here.

## Act one: a parser
*where our protagonists meet each other behind the keyboard*

- D: Geert, what is a parser actually?
- G: Glad you asked Daan! A parser is a function that transforms text into objects.
- D: So this would be a good signature

```kotlin
(String) -> T
```

- G: Hmmm, almost
- D: What is wrong with it?
- G: Well, what would you return when you expect a number and the text is "banana"?
- D: Ah, I see. We are missing the possibility to express a failure. Would this be better?

```kotlin
(String) -> T?
```

- G: Yes, this is somewhat better... But we can do even better!
- D: How?
- G: What if we could combine functions with this "parser signature" in a way that they become bigger and better parsers?
- D: Sounds great! How would we go about that?
- G: Say a text that starts with a number, but ends with "+10", we would like the next parser to continue where we left off. So we need a way to return this left-over text.
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
- G: I did! But maybe it is better to implement a few simple parsers first. Just to play with the signature and the concept a little bit.
- D: Good idea! What about a parser that parses a single `'A'` from a String?
- G: Yes, good one! What would you suggest?
- D: I think the following does implement the `Parser<Char>` signature:

```kotlin
fun parseA(input: String): Pair<Char, String>? =
    if (input.startsWith('A')) {
        'A' to input.drop(1)
    } else {
        null
    }
```

- G: It does! Would it not be great if you could make this more generic?
- D: Do you want to accept the character to match? Like so

```kotlin
fun character(needle: Char): Parser<Char> = { input ->
    if (input.startsWith(needle)) {
        needle to input.drop(1)
    } else {
        null
    }
}
```

- D: Wait, I am actually returning a function!
- G: That is what functional programming is all about.
- D: My head hurts a little, and it's late now, lets get some rest and continue tomorrow?
- G: Okay! Goodnight!

## Act two: combining parsers

*After a good night sleep our protagonists meet again*  

- G: Hi Daan, are you well rested, ready to go?
- D: I am well rested, thank you. I was dreaming about parsers. You mentioned that it would be nice to combine them. How does that work?

- G: Well, let's say you have a parser for an `'A'` and a parser for a `'B'`. You want to have a parser that parses either 'A' or 'B'.
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
- G: If we can do `OR` we can do `AND`.
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

- G: Looking good! But the parser returns a `Pair`, what if you want something else?
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
("AB", "C")
```

- D: The result is no longer a `Pair<Char, Char>`, but a `String`! Just as expected!
- G: What other combinators can you think of?
- D: Let me see... zero or more occurrences of `'A'`?
- G: That is a good suggestion.
- D: Hmm, let me think of an implementation.

```kotlin
fun <T> star(p: Parser<T>): Parser<List<T>> = { input ->
    val result = p(input)
    if (result != null) {
        val (match, intermediate) = result
        val (remaining, rest) = star(p)(intermediate)
        (listOf(match) + remaining) to rest
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
(['A', 'A', 'A', 'A', 'A'], "B")
```

- G: It works, but looking at the code it feels a bit ... imperative.
- D: What do you suggest?
- G: We can express the star operation in terms of 'and', 'or' and 'map'
- D: That is a novel idea! Let me puzzle on this one:

```kotlin
fun <T> star(p: Parser<T>): Parser<List<T>> =
    or(
        map(and(p, star(p))) { (r, rs) -> listOf(r) + rs },
        { input -> emptyList<T>() to input }
    )
```

- G: Very nice! You have defined the star combinator recursively in terms of itself! This compiles, but will give you an error on runtime. Shall we see?
- D: Yes! Let's go!

```plain
Exception in thread "main" java.lang.StackOverflowError
	at kotlin.jvm.internal.Intrinsics.checkNotNullParameter(Intrinsics.java:130)
	at com.alliander.ptutools.TempJavaMagParserKt.star(TempJavaMagParser.kt)
	at com.alliander.ptutools.TempJavaMagParserKt.star(TempJavaMagParser.kt:62)
	at com.alliander.ptutools.TempJavaMagParserKt.star(TempJavaMagParser.kt:62)
	...
```

- D: Oops! We need a bigger stack!
- G: No, we should delay the evaluation of the inner star combinator to when we really need it!
- D: I am puzzled, what do you mean?
- G: We can make the evaluation lazy.

```kotlin
fun <T> lazy(parserProducer: () -> Parser<T>): Parser<T> = { input ->
    parserProducer()(input)
}

fun <T> star(p: Parser<T>): Parser<List<T>> =
    or(
        map(and(p, lazy { star(p) } )) { (r, rs) -> listOf(r) + rs },
        { input -> emptyList<T>() to input }
    )
```
- D: I am so excited, and I just can't hide it!

```kotlin
fun main() {
    val p = star(character('A'))
    println(p("AAAAAB"))
}
```

```plain
(['A', 'A', 'A', 'A', 'A'], "B")
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

- D: Wow, this combinator-stuff is powerful, but also draining. Can we pick this up again tomorrow?
- G: Sure thing!

## Act three: functional parser

*Both our protagonists went for a walk outside to clear their heads, before coming back to the problem at hand*.

- D: How are you today, Geert?
- G: Great! Do you want to continue playing around with parser combinators?
- D: Yes, I would like to continue. I want to parse the following kind of binary expressions:

```plain
110010+1100-10010-110+100
```

- D: It should return "the answer".
- G: We can surely parse such expressions with our library of combinators.
- G: What parts do you recognize in your expression
- D: Hmmm,... I see binary numbers, like `110` and `110010`, as well as operators `+` and `-`.
- G: Let's focus on the numbers first.
- D: Okay. Let me give it a try.

```kotlin
fun number(): Parser<Int> =
    and(leadingDigit(), star(digit()))

fun leadingDigit() =
    character('1')

fun digit() =
    or(character('0'), character('1'))
```

G: This will not compile because you did not map the result to an `Int`.
D: You are so smart!

```kotlin
fun number(): Parser<Int> =
    map(and(leadingDigit(), star(digit()))) { (digit, digits) ->
        (listOf(digit) + digits).fold(0) { acc, d ->
            2 * acc + d
        }
    }

fun leadingDigit(): Parser<Int> =
    map(character('1')) {_ -> 1}

fun digit(): Parser<Int> =
    or(map(character('0')) {_ -> 0}, map(character('1')) {_ -> 1})
```
- G: Let's see if it will parse a binary number.
- D: I am confident, that it will work!

```kotlin
fun main() {
    val p = number()
    println(p("101010"))
}
```

```plain
(42, )
```

- D: It works like a charm!
- G: Let's move on to the operators.
- D: I think I have it

```kotlin
fun operator() =
    or(character('+'), character('-'))
```

- G: Aren't you forgetting something?
- D: Of course! I should map it to something that we need.
- G: Exactly.
- D: Would this work?

```kotlin
fun operator(): Parser<(Int, Int) -> Int> =
    or(
        map(character('+')) {_ -> Int::plus },
        map(character('-')) {_ -> Int::minus },
    )
```

- G: Excellent!
- G: Now you are ready to write the entire parser, my young Padawan!
- D: Am I?
- D: I will try

```kotlin
fun expression(): Parser<Int> =
    or(
        map(and(
            and(number(), operator()),
            lazy(::expression),
        )) { (first, right) ->
            val (left, op) = first
            op(left, right)},
        number()
    )
```

- D: I think this works
- G: Let's try it

```kotlin
fun main() {
    val p = expression()
    println(p("100000+1000+10"))
}
```

```plain
(42, )
```

## Closing Thoughts

*Later the protagonists gathered for a final time*.

- D: Shall I commit this to the repository?
- G: No!
- D: Why not?
- G: Although there is nothing wrong with the code we have written, it is not complete nor battle-tested.
- G: You learned a valuable tool, but we should seek a library that fits our needs
- D: 🤦
- D: You are right, I got curried away in my enthusiasm.

## Assignments
The `number` parser in the functional parser section is not complete. It does not parse the valid binary number `0`. Can you fix the `number` parser to accept `0` as well?

The ergonomics of the combinator leave something to be desired. Can you write an `and`/`or` parser that allows any number of arguments?

There is a subtle bug with associativity in the expression parser. Did you catch that? Can you fix it?
