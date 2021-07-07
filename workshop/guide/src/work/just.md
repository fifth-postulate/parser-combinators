# Just all
Some parsers accept only a part of their input. This is generally fine for intermediate parsers, but you generally want to accept the entire input for top-level parsers.

The `just` parser does just that. It accepts a parsers and will only succeed when the remaining input is empty.

| input | *just(character('A'))* |
|-------|------------------------|
| "ABC" | []                     |
| "A"   | [('A', "")]            |
| "aBC" | []                     |

## Implementation
Once we parsed our input, we need to remove the results that did not consume the entire input.

```
just(p) =
    input => filter ((r, rest) => rest == "") (p input)
```

## Exercises
1. What is the type of the `just` parser?
2. Implement the `just` parser.
