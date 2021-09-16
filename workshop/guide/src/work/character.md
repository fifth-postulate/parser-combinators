# Any character
The work we have done for the parsing of a character `'A'`, could also be done for other characters.

One could copy the implementation of *parserA* and change it into a *parserB*, a *parserC* or a *parser^* for that matter.

Instead we will define a *character* function that accepts the character to parse as an argument.

That is, *character* is a parser that accepts a character as argument and returns a parser that succeeds if the input starts with that character. Below we summarize our expectation for *character*.

| input | char | *character(char)* |
|-------|------|-------------------|
| "ABC" | 'A'  | [("A", "BC")]     |
| "BC"  | 'A'  | []                |
| "aBC" | 'A'  | []                |
| "aBC" | 'a'  | [("a", "BC")]     |

## Implementation

The implementation of the `character` function has a lot in common with `parserA`.

```
character(char) =
    input => if input.startsWith(char) then
                [(char.toString(), input[1:])]
             else
                []
```

## Exercises
1. What is the type of the `character` function?
1. Implement the `character` function.
2. Implement `parserA` in terms of `character`. This is a common practice in this workshop. When you notice a common pattern in parsers, generalize it.
