# Aritmetic Expression
In this chapter we will parse aritmetical expression. For us that will mean expressions involving integers, variables, addition, subtraction, multiplication and division. An example of such an expresion would be `5*2+1`.

## Grammar
The grammar for our expression is as follows

```
Expression ::= Term ('+'|'-') Expression | Term
Term ::= Factor ('*'|'/') Term | Factor
Factor ::= Constant | Variable | '(' Expression ')'
Constant ::= Digit+
Variable ::= Alpha+
Digit ::= '0' | '1' | '2' | ... | '9'
Alpha ::= 'a' | 'b' | ... | 'z | 'A' | 'B' | ... | 'Z'
```

## Data Structure
A data structure that can represent an expression should cater for the following variants

1. Constant, containing the integer it represents
2. Variable, containing the name of a value in the context.
3. and the four operators, Addition, Subtraction, Multiplication, Division

## Exercises
1. Create a parser that can parse an expression.
2. Create an evaluate function that takes an expression and a context and returns the corresponding value.