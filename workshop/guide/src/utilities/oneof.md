# Oneof the following parsers
What `consecutive` is to `and`, `oneOf` is to `or`.

Just like the case for the `and` parser combinator, the `or` parser combinator only accepts two parsers. Often there a multiple alternatives that you want to combine. For example, when you are creating a full-blown programming language, an expression could be a number of things. Among the options there would be

* an assignment
* an arithmetical expression
* a logical expression
* a ternary expression

## Exercises
1. What is the signature of the `oneOf` parser combinator.
2. Implement the `oneOf` parser combinator.