# character `A`
One of the simplest parsers to consider is a parser that succeeds if the input starts with the character `'A'`.

For example. *parserA* is a parser that succeeds if the input starts with the character `'A'`. Below we summarize our expectation for *parserA*.

| input | *parserA.parse(input)* |
|-------|------------------------|
| "ABC" | [("A", "BC")]          |
| "BC"  | []                     |
| "aBC" | []                     |

## Implementation

```
parserA() =
    input => if input.startsWith('A') then
                [("A", input[1:])]
             else
                []
```

## Exercises
1. Implement `parserA` in your favorite language. You will have to choice how to represent functions.