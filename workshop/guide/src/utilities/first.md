# Commit to the first result
Sometimes you are not interested in all possible ways of parsing input into a data structure. You are happy with the first successful parse.

The `first` parser does precisely that. Instead of returning all the possible parsing of an input, it only returns the first.

## Exercises
1. What is the signature of the `first` parser.
2. Implement the `first` parser.
3. How does the order of alternatives influence the semantics of `first`.