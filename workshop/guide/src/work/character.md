# Any character
The work we have done for the parsing a character `'A'`, could also be done for other charcters.

One could copy the implementation of *parserA* and change it into a *parserB*, a *parserC* or a *parser^* for that matter.

Instead we will define a *character* parser that accepts the character to parse as an argument.

For example. *character* is a parser that accepts a character as argument and returns a parser that succeeds if the input starts with that character. Below we summarize our expectation for *character*.

| input | char | *character(char).parse(input)* |
|-------|------|--------------------------------|
| "ABC" | 'A'  | [("A", "BC")]                  |
| "BC"  | 'A'  | []                             |
| "aBC" | 'A'  | [("a", "BC")]                  |

## Implementation

```
character(char) =
    input => if input.startsWith(char) then
                [(char.toString(), input[1:])]
             else
                []
```