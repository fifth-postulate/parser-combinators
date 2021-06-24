# Separating parser
Describing list, or other repetitive structures, often have some sort of separator that distinguish between where one item stops and the next begins. In the case of a list of integers, a `','` would be used as seperator. In some programming languages the `';'` is used for similar purpose.

The `separatedBy` parser combinator will be used in this situation. It accepts two parser. One parser will parse the separator, the other the structure that is separated.

## Exercises
1. What is the signature of the `separatedBy` parser combinator?
2. Implement the `separatedBy` parser combinator.