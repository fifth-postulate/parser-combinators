# Being lazy
Up until now, our parser combinators are not [recursive](https://en.wikipedia.org/wiki/Recursion). Every combinator accepts one or more parsers and use combine them into a more complex parser.

What we are about to do is define parser combinators that are defined in terms of *themselves*.

This can be problematic. The problem is that our combinators have to return a parser. They can't predict how deeply nested a parser needs to be called. Take a look at the following grammar.

```
A ::= A | a;
```

This grammar accepts any number of the character 'a'. If we naively write a parser we would might write the following.

```
A =
    or(A(), character('a'))
```

The thoughts are: Our parser `A` is either

1. our parser `A`
2. a character `'a'`.

The first part is problematic, because when it calls the `A` parser it will go down an infinite path. If we would try to call `A` we would need to repeatedly call `A` without bounds.

* `A()`
* `or(A(), character('a'))`
* `or(or(A(), character('a')), character('a'))`
* `or(or(or(A(), character('a')), character('a')), character('a'))`
* `or(or(or(or(A(), character('a')), character('a')), character('a')), character('a'))`
* ...

## Solution
One way to solve this problem is to delay the call to yourself. What we instead would like to say is, when it is time to parse some input I will produce a the parser that is going to do the parsing. So there are two ingredients: a parser producer, and a combinator that works in the above manner.

### Parser producer
We could use a function that accepts a dummy value as a producer. It's signature would be `() -> string -> [(T, string)]` or, using the parser alias, `() -> parser T`.

One could implement a producer with an anonymous function. Say we have a parser `A`, a producer of `A` could be implemented as

```
_ => A
```

Here we use an underscore `_` to signify that we are not interested in the actual value.

### combinator
The last ingredient is a combinator that accepts a producer for a parser and uses returns a parser that uses it.

```
lazy(p) =
    input => (p())(input)
```

This might be hard to read. The first parenthesized part `(p())` calls the producer. It will return a parser. That parser is then called with the input to return the parse result.

## Exercises
1. What is the type of the `lazy` combinator?
2. Implement the `lazy` combinator.
3. Is it possible not to use recursion?