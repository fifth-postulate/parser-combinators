# Parse many things
Parsing a single character, or a single character and then an other, will only get you so far. Sometimes you want to accept many things. This chapter introduces a parser combinator that does exactly this.

The `many` combinator accepts a parser `p` and returns a parser that accepts input precisely when `p` succeeds any number of times.

| Input | *many(character('A'))*                       |
|-------|----------------------------------------------|
| ""    | [([], "")]                                   |
| "A"   | [(['A'], ""), ([], "A")]                     |
| "AA"  | [(['A', 'A'], ""), (['A'], "A"), ([], "AA")] |
| "aAA" | [([], "aAA")]

This is the first time that a parser returns multiple possible results. The different results correspond with the different options. Any number of times include

* zero times
* one time
* two times
* three times
* etcetera

This can be seen in our results. In the example with the input `"AA"` the parser `many(character('A'))` can parse

1. Two 'A's, with no remaining input.
2. One 'A', with remaining input "A".
3. Zero 'A's, with remaining input "AA".

All these results are returned, in the order from most input consumed to least input consumed.

## Implementation
The implementation of the `many` parser is defined in terms of itself. The `lazy` combinator allows a call to the parser you are defining without blowing the call stack. Our pseudo-code had this build in 😁.

```
many(p) =
    or(map(combine, andThen(p, many(p))), succeed([]))
```

We are relying on combinators from earlier chapters. Because of this the implementation is not as exact, leaving some ideas to be explored.

## Exercises
1. What is the type the `many` parser?
2. What is the type of the `combine` function used to map the result of parser `p` and then `many(p)`?
3. Implement the `combine` function.
4. Implement the `many` combinator. Don't forget to be lazy. 
5. The `many` combinator parses *zero* or more items. Create a combinator `many1` that parser *one* or more items.