# Parse this and then that
What we are going to combine in this chapter is two parsers with the intent of parsing with the first parser **and** then parse the remaining input with the second parser.

Let's assume again that we have parser `A = character('A')` and `B = character('B')`. The expectations of the `and` parser are

| input  | *and(A, B)*      |
|--------|----------------------|
| "ABC"  | [(('A', 'B'), "C")]  |
| "ABCD" | [(('A', 'B'), "CD")] |
| "aBC"  | []                   |

Note that when the parser accepts its input, it returns a pair as a result. In our example it is a pair of characters, but it depends on the parser that we are combining.

## Exercises
1. What is the type of the `and` parser?
2. Create the `and` parser
3. If you wanted to parse more than two consecutive parsers, how would you implement that?

Depending on your language it would be better to return a list of results instead of a pair. 