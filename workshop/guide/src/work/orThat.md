# Parse this or that
With a basic parser under our belt, we start looking at a combinator. A combinator is a function that *combines* simpler things into a more complex thing.

Sometimes we have an option. Either we want to parse a number, or we want to parse a variable name. If we have simpler parsers that parse a number and another that parse a variable name, we can combine them into a more complex that could parse either of the two.

Assuming we have parsers `A = character('A')` and `B = character('B')`, below you find a table of our expectations for the `oneOf` combinator.

| input | oneOf(A, B).parse(input) |
|-------|--------------------------|
| "ABC" | [("A", "BC")]            |
| "BAC" | [("B", "AC")]            |
| "aBC" | []                       |

## Signature
Remember, the type of a parser is `string -> [(T, string)]` where `T` is a type variable representing the structure that is returned for this parser.

A combinator is a [higher order function][wikipedia:higher-order-function]. I.e.

> a function that does at least one of the following:
>
> * takes one or more functions as arguments,
> * returns a function as its result.

In our case it does both. We accept two parsers, which themselves are functions and in turn returns a parser, again a function. The type of the `oneOf` is

```
oneOf : (string -> [(T, string)]) -> (string -> [(T, string)]) -> (string -> [(T, string)])
```

or, if we abbreviate `string -> [(T, string)]` to `parser T`, it could read

```
oneOf : parser T -> parser T -> parser T
```

I.e. oneOf is a function that accepts two parsers and returns a third.

## Implementation
When we think about an implementation of `oneOf` we need to consider the two parsers that are used to construct the result parser. If we have a parser `A` that produces output `resultA` on input `I`, and a parser `B` that produces output `resultB` on input `I`, parser `oneOf(A, B)` should produce the concatenation of `resultA` and `resultB` on input `I`.

```
oneOf(A, B) =
    input => A(input).concat(B(input))                []
```

## Exercises
1. Implement the `oneOf` combinator.
2. If you want choose between multiple options to parse, how would you implement `oneOf` in that case?

[wikipedia:higher-order-function]: https://en.wikipedia.org/wiki/Higher-order_function
