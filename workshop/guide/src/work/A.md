# character `A`
One of the simplest parsers that does depend on the input to parse is a parser that succeeds if the input starts with the character `'A'`.

For example. *parserA* is a parser that succeeds if the input starts with the character `'A'`. Below we summarize our expectation for *parserA*.

| input | *parserA()(input)* |
|-------|--------------------|
| "ABC" | [("A", "BC")]      |
| "BC"  | []                 |
| "aBC" | []                 |

## Implementation

Below you can find some pseudo code for the `parserA` function.

```
parserA() =
    input => if input.startsWith('A') then
                [("A", input[1:])]
             else
                []
```

## Exercises
1. Implement `parserA` in your favorite language. From now on, implement will also mean verify your implementation.