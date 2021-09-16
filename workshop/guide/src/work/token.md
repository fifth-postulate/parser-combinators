# Parse a token
Instead of parsing a single character, we could try to parse a word or *token*.

Assume we want to know if an input starts with a name, for example `"Geert"`. But you yourself could be interested in a different name. So we would like to create a function that accepts a token and returns a parser that accepts input whenever it starts with that token.

Below you find some expectations we have for the `token` parser.

| input                     | t       | *token(t)*                        |
|---------------------------|---------|-----------------------------------|
| "Geert is a father"       | "Geert" | [("Geert", " is a father")]       |
| "if a == 1 then 1 else 2" | "if"    | [("if", " a == 1 then 1 else 2")] |
| "Daan is a father"        | "Geert" | []                                |
| "while a == 1 do nothing" | "if"    | []                                |

## Implementation
The implementation of `token` is very similar to the `character` implementation. This is in part because we reuse our `startsWith` function. It magically accepts both characters and strings! Real languages might not be so flexible.

```
token(t) =
    input => if input.startsWith(t) then
                [(t, input[t.size:])]
             else
                []
```

We use our Python inspired notation `input[n:]` to snip of the the length of the token `t` from our input.

## Exercises
1. What is the type of the `token` function?
2. Implement `token`.
