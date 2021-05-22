# Transform a result
Sometimes you end up with a parser for a certain structure `A`, but you want to have a parser for an other structure `B`, which you now how to make from a structure `A`. For example, you have a parser that accepts a character `'7'`, but you want to have a parser that accepts the corresponding number `7`.

The `map` parser combinator will help you out in this situation. It will accept a parser `string -> [(A, string)]` and turn is into a parser `string -> [(B, string)]`. It will do this if you provide a transform function `A -> B`.

| Input | parser           | transform | *map(transform, parser)* |
|-------|------------------|-----------|--------------------------|
| "123" | satisfy(isDigit) | toInt     | [(1, "23")]              |
| "ABC" | satisfy(isDigit) | toInt     | []                       |

## Implementation
For the `map` combinator we rely on the a function that can transform a list in the same way. We only need to change the first arguments of the tuples in our result list.

```
map(f, parser) =
    input =>
        listMap ((r, rest) => (f(r), rest)) (parser input)
```

## Exercises
1. What is the type of `map`
2. Implement the `map` combinator.
3. If your languages does not have a `listMap`, how would you implement that?