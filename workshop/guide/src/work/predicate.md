# Parse a Predicate
Just like we generalized `characterA` into `character`, we are going to generalize `character` into something we will call `satisfy`.

The `satisfy` parser will accept a [_predicate_][wikipedia:predicate]. A predicate is a function that accepts a character and returns a `boolean`.

```
char -> boolean
```

As an example how we would want to use the `satisfy` function, we could write the following equation.

```
characterA == satisfy(c => c == 'A')
```

Our expectations for the `satisfy` parser are

| input | predicate     | *satisfy(predicate)* |
|-------|---------------|----------------------|
| "ABC" | c => c == 'A' | [('A', "BC")]        |
| "aBC" | c => c == 'a' | [('a', "BC")]        |
| "ABC" | c => c == '!' | []                   |

## Implementation
The `satisfy` parser is not that different from either the `symbol` or `token` parser. The difference are the clause in the `if`-expression, and how we return our input.

```
satisfy(predicate) =
    input => if predicate(input[0]) then
                [(input[0], input[1:])]
             else
                []
```

## Exercises
1. What is the type of the `satisfy` parser?
1. Implement the `satisfy` parser.
2. Implement the `character` parser in terms of the `satisfy` parser.
3. Does your implementation work correctly when the input is the empty string?

[wikipedia:predicate]:https://en.wikipedia.org/wiki/Predicate_(mathematical_logic)
