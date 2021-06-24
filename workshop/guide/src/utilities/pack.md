# Packing parser
Often we want to parse some data that is surrounded with some structure. For example, some programming languages delineate blocks of code by a matched pair of curly braces. A literal list of items starts with an opening bracket and is finished with a closing bracket.

The `pack` parser combinator accepts three parser. It "packs" the second parser in between the opening parser and the closing parser.

## Exercises
1. What is the signature of the `pack` parser combinator?
2. Implement the `pack` parser combinator.
3. Define a parser combinator `parenthesized` that packs a parser with a matching pair of parenthesis.
4. Simiarly define a `bracketed` parser.