# Ignore the result
Sometimes you are not that interested in the result of a parser. For example, if you are parsing a tuple at one point you need to parse the character `'('`. But you don't really need the actual character. You just want to make sure that the parse succeeds.

This is where the `ignore` parser combinator comes in. It accepts a parser `p` and parses the same input as `p` would do. The only difference is that it ignores the resulting value. Instead it returns a dummy value.

## Exercises
1. What is the signature of the `ignore` parser combinator?
2. Implement the `ignore` parser.
