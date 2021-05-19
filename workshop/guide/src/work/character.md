# Any character
The work we have done for the parsing a character `'A'`, could also be done for other characters.

One could copy the implementation of *parserA* and change it into a *parserB*, a *parserC* or a *parser^* for that matter.

Instead we will define a *character* parser that accepts the character to parse as an argument.

For example. *character* is a parser that accepts a character as argument and returns a parser that succeeds if the input starts with that character. Below we summarize our expectation for *character*.

| input | char | *character(char)* |
|-------|------|-------------------|
| "ABC" | 'A'  | [("A", "BC")]     |
| "BC"  | 'A'  | []                |
| "aBC" | 'A'  | []                |
| "aBC" | 'a'  | [("a", "BC")]     |

## Implementation

The implementation of the `character` parser has a lot in common with `parserA`.

```
character(char) =
    input => if input.startsWith(char) then
                [(char.toString(), input[1:])]
             else
                []
```

## Exercises
1. What is the type of the `character` parser.
1. Implement the `character` parser.
2. Implement `parserA` in terms of `character`. This is a common practice in this workshop. When you notice a common pattern in parsers, generalize it.