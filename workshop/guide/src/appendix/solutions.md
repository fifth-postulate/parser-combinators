# Solutions
In this section you will find solutions some of the exercises of this workshop.

The reason we included these answers is to be self-contained. We would much prefer if you would discuss your solutions with others and discuss the reasons why you decided to answer the questions in the manner you did.

## Prelimenaries
### Languages
1. The language of all the wrong ways of spelling bananan: `{"banan", "bananana", "ban", ...}`
2. I think the English languages is finite for it is not possible to concatenate words in the English languages and form valid words. For this reason I think that Dutch is an infinite language.

### EBNF
1. Using the natural number from the chapter we could have

```
addition ::= number ("+" number)?
```

### Types
1. The types describes a function that accepts a string and returns a list of pairs of string and integer. An example of such functions would be a function that determines the frequency of all the words that occur in a text.
2. `S -> U -> T`.

### Style
1. Currently, I am very interested in the [Kind programming language](https://github.com/uwu-tech/Kind). It is a dependently typed, functional programming language.
2. I like a lot of styles. I am particular fond of logic programming.
3. I worked through the workshop various times. I have picked object oriented styles and functional styles.

### Parser
1. `t` is the twentiest letter in the alphabet. Including the digits '0' through '9' we have 30 options. So base 30 is an option. Note that there are other options as well.
2. If using an object oriented programming language, I could implement the following interface. Each parser object should have a method called `parse` that accepts a string and returns a list of paris of `T` and `string`.
3. I would introduce a result type that differentiates between the return values of a parser: failed, succesfull.
4. I would keep track of the the position in the original string the remaining input is from. This way I could provide some extra information when a parse failed.

## Workshop
Some of the questions in this chapter ask for an implementation. See the example code accompanying this material for possible ways of implementing the `succeed` and `fail` parsers.

### Succeed and Friends
1. The `succeed("Hello, World!")` is a parser that does not consume the any input and always returns `"Hello, World"`.

### Character A
1. `string -> [(string, string)]`.
3. If my language of choices does not have a `startsWith` function but does allow indexing into the string I would implement it by examining the 0th character of the string.

### Any character
1. `string -> string -> [(string, string)]`. At least this is what the examples show. It would be better if the examples would return the actual character. Then the signature would be `char -> string -> [(char, string)]`.
3. `characterA = character('A')`.

### Parse a Token
1. `string -> string -> [(string, string)]`.

### Parse a predicate
1. `(char -> boolean) -> string -> [(char, string)]`
3. `character(target) = satisfy(actual => actual == target)`
4. Why, of course. Thank you for asking.

### Parsing this or that
2. If I would want to accept more than one parser as an alternative, I would accept a list of parsers. The implementation would concatenate all the results from calling the given parsers on the input.

### Parsing this and that
1. `(string -> [(A, string)]) -> (string -> [(B, string)]) -> string -> [((A, B), string)]`. Or with the `parser T` alias: `parser A -> parser B -> parser (A, B)`.
3. If I would want to accept more than one parse as an string those parsers together, I would accept a list of parsers. I would implement a recursive call differentiating between an empty list and a non-empty list.

### Just All
1. `parser T -> parser T`.

### Transform a Result
1. `(A -> B) -> parser A -> parser B`.
3. I would implement a recursive function, differentiating between an empty list and a non-empty list.

### Being Lazy
1. `(() -> parser T) -> parser T`.
3. Yes, but this highly depends on your language.

### Parse Many Things
1. `parser T -> parser [T]`.
2. `(T, [T] -> [T]`.
5. `many1(parser) = map(combine, and(parser, many(parser)))`.


## Utilities
### Ignore the Result
1. `parser T -> parser ()`.

### Commit to the First Result
1. `parser T -> parser T`.
3. The order in which influence the semantics of first. It changes from the longest match to the shortest match.

### Greedy Parsing
1. `parser T -> parser T`.
3. `greedy1(parser) = map(combine, and(parser, greedy(parser)))`.

### Consecutive Parsers
1. `[parser T] -> parser [T]`. This forces all the parsers to return similar things.

### One of the Following Parsers
1. `[parser T] -> parser T`.

### Whitespace
1. `parser ()`.
3. `parser T -> parser T`.

### Packing Parser
1. `parser L -> parser T -> parser R -> parser T`.

### Separating Parser
1. `parser string -> parser T -> parser [T]`.

## Projects
### List of Things
1. `parser T -> parser [T]`.

### Balanced Parenthesis
3. `parser Tree`.

### Template Language
1. `string -> T`. A dictionary.
2. `parser ((string -> T) -> string)`. This signature assumes that a key is not present in the dictionary, the key is used as a default.

### Arithmetic Expression
> This paragraph intentionally is left blank
