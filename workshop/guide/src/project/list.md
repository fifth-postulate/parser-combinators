# List of things
The first project we are going to take on is to parse a list of elements. For demonstration purposes the element will be either a character `'A'` or a character `'B'`. This way we can focus on the the list part of parsing a list. Do make sure to accept a the element parser as a parameter. This makes it easy to change the element parser later on.

For the record, a list is denoted by:

1. An opening bracket `'['`.
2. A comma seperated list of elements.
3. A closing bracket `']'`.

The `list` parser combinator accepts an element parser and returns a parser that can parse a list of elements.

## Exercises
1. What is the signature of the `list` parser combinator.
2. Implement the `list` parser combinator, making use of the combinators in your framework.
3. Create a parser that parses either a character `'A'` or a character `'B'`.
4. Create a parser that parses a list of `'A'`s and `'B'`s.