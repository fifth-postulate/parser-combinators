# Parse a Predicate
Just like we generalized `characterA` into `character`, we are going to generalize `character` into something we will call `satisfy`.

The `satisfy` function will accept a [_predicate_][wikipedia:predicate], and returns a parser that will succeed when the input starts with a character that fulfills the predicate. A predicate is a function that accepts a character and returns a `boolean`.

```
char -> boolean
```

As an example how we would want to use the `satisfy` function, we could write the following equation.

```
characterA == satisfy(c => c == 'A')
```

Here we have introduced the notation for a function without a name: `c => c == 'A'`. This notation can be read as follows

1. The part before `=>` names the parameter of the function.
2. The part after `=>` is the body of the function.

In our example we have denoted a function that accepts a character `c` and returns true when that character equals `'A'`.

Our expectations for the `satisfy` function are

| input | predicate     | *satisfy(predicate)* |
|-------|---------------|----------------------|
| "ABC" | c => c == 'A' | [('A', "BC")]        |
| "aBC" | c => c == 'a' | [('a', "BC")]        |
| "ABC" | c => c == '!' | []                   |

## Implementation
The `satisfy` function is not that different from either the `character` or `token` functions. The difference are the clause in the `if`-expression, and how we return our input.

```
satisfy(predicate) =
    input => if predicate(input[0]) then
                [(input[0], input[1:])]
             else
                []
```

## Exercises
1. What is the type of the `satisfy` function?
1. Implement `satisfy`.
2. Implement the `character` function in terms of the `satisfy` function.
3. Does your implementation work correctly when the input is the empty string?

[wikipedia:predicate]:https://en.wikipedia.org/wiki/Predicate_(mathematical_logic)
