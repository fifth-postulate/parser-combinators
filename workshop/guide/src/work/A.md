# character `A`
One of the simplest parsers that does depend on the input to parse is a parser that succeeds if the input starts with the character `'A'`.

For example. *parserA* is a parser that succeeds if the input starts with the character `'A'`. Below we summarize our expectation for *parserA*.

| input | *parserA()*   |
|-------|---------------|
| "ABC" | [("A", "BC")] |
| "BC"  | []            |
| "aBC" | []            |

## Implementation

Below you can find some pseudo code for the `parserA` function.

```
parserA() =
    input => if input.startsWith('A') then
                [("A", input[1:])]
             else
                []
```

Here `input[1:]` means the rest of the input starting with the *second* character, since our lists indices are zero-based. People familiar with Python will feel comfortable with this notation.

## Exercises
1. What is the type of `parserA`
2. Implement `parserA` in your favorite language. From now on, implement will also mean verify your implementation.