# character `A`
One of the simplest parsers that depends on any input to parse is a parser that succeeds if the input starts with the character `'A'`.

For example, *parserA* is a parser that succeeds if the input starts with the character `'A'`. Below we summarize our expectation for *parserA*.

| input | *parserA()*   |
|-------|---------------|
| "ABC" | [("A", "BC")] |
| "BC"  | []            |
| "aBC" | []            |

## Implementation
Below you can find some pseudo code for the `parserA` function.

The implementation of the `parserA` function relies on the capabilities of your language to work with `string`. Here we assume that your language can tell you when a certain string start with an other character. See the exercises for some pointers on how to solve the problem if your language does not provide such functionality.


```
parserA() =
    input => if input.startsWith('A') then
                [("A", input[1:])]
             else
                []
```

Here `input[1:]` means the rest of the input starting with the *second* character, since our lists indices are zero-based. People familiar with Python will feel comfortable with this notation.

## Exercises
1. What is the type of `parserA`?
2. Implement `parserA` in your favorite language. From now on, implement will also mean verify your implementation.
3. If your language does not have a `startsWith` function for `string` how would you implement it? What tools are provided for working with `string`?
