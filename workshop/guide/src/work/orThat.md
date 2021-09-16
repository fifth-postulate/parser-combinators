# Parse this or that
With some basic parsers under our belt, we can start looking at a combinator. A combinator is a function that *combines* simpler things into a more complex thing. In our case, it combines simpler parsers into a more complex parser.

Sometimes you find yourself at a cross road. Either we want to parse a number, or we want to parse a variable name. If we have simpler parser that can parse a number and another parser that can parse a variable name, we can combine them into a more complex parser that could parse either of the two.

Assuming we have parsers `A = character('A')` and `B = character('B')`, below you find a table of our expectations for the `or` combinator.

| input | *or(A, B)*    |
|-------|---------------|
| "ABC" | [("A", "BC")] |
| "BAC" | [("B", "AC")] |
| "aBC" | []            |

## Signature
Remember, the type of a parser is `string -> [(T, string)]` where `T` is a type variable representing the structure that is returned for this parser.

A combinator is a [higher order function][wikipedia:higher-order-function]. I.e.

> a function that does at least one of the following:
>
> * takes one or more functions as arguments,
> * returns a function as its result.

In our case it does both. We accept two parsers, which themselves are functions and in turn returns a parser, again a function. The type of `or` is

```
or : (string -> [(T, string)]) -> (string -> [(T, string)]) -> (string -> [(T, string)])
```

or, if we abbreviate `string -> [(T, string)]` to `parser T`, it could read

```
or : parser T -> parser T -> parser T
```

I.e. `or` is a function that accepts two parsers and returns a third.

## Implementation
When we think about an implementation of `or` we need to consider the two parsers that are used to construct the result parser. If we have a parser `A` that produces output `resultA` on input `I`, and a parser `B` that produces output `resultB` on input `I`, parser `or(A, B)` should produce the concatenation of `resultA` and `resultB` on input `I`.

```
or(A, B) =
    input => A(input).concat(B(input))
```

## Exercises
1. Implement the `or` combinator.
2. If you want choose between multiple options to parse, how would you implement `or` in that case?

[wikipedia:higher-order-function]: https://en.wikipedia.org/wiki/Higher-order_function
