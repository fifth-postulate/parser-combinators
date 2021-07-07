# Consecutive parsers
The `and` parser is a happy little parser combinator. It takes two parsers and runs them consecutively, feeding the remaining input of the first parse into the second parser, returning the pair of results.

The only problem with the `and` parser is that it only accepts two parsers. Often you find yourself wanting to parser any number of parsers consecutively. For example, in order to parse a pair of a name and an integer, you would have to parse the following structures

1. Parse a character `'('`.
2. Parse any name.
3. Parse a character `','`.
4. Parse any integer.
5. Parse a character `')'`.

And combine the result of the name parser and the integer parser into a pair of the two.

The `consecutive` parser will be build for this task. It accepts a sequence of parsers and a way to combine the intermediate results into a final result.

## Exercises
1. What is the signature of the `consecutive` parser? Depending on the language you are working with, this can be a tricky question. Don't be afraid to sacrifice generality for practicality.
2. Implement the `consecutive` parser.
3. Use the `consecutive` parser to parse the tuple from the example.