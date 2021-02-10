# Types and Styles
Not every programming language is the same. Among the characteristics in which programming languages can differ, we will highlight the following:

* Types
* Styles

## Types
A [_type system_][wikipedia:type-system] is

> a logical system comprising a set of rules that assigns a property called a type to the various constructs of a computer program, such as variables, expressions, functions or modules.

which isn't really helpful, if you don't already know what a type is.

One could say that types are used to prevent certain mistakes. For example, a type system could prevent the addition of a number with a string, something that does not make sense in every situation. If you want to know more about types and how they are used we recommend [_Types and Programming Languages_][book:tapl] by Benjamin Pierce.

Not every programming language uses types, and the programming languages that do aren't always explicit about it. Even though this workshop is language agnostic, meaning that we don't use a concrete language to express the ideas, we can benefit from discussing types.

In order to fascilitate the discussion we will introduce a type notation. This is a description of a certain type.

### Notation
In order get start the discussion we need to be able to express certain base types. Since we are talking a lot about languages, which are just a collection of _string_s we are going to include those in are notation.

#### Base
The notation for base types include `string`, `integer`, `boolean` etcetera. These will form building blocks for more complex types. The are written with all lower-case letters.

#### Type Variables
Sometimes we need a type, but aren't interested in which concrete type it is. We will use _type variables_ for that. For example `T` is a type variable. It could be a `string`, `integer` or any other type. The are written with a starting capital letter. Often we will use a single letter. When we want to convey intent on how the type is used we could use words.

#### Functions
Functions are important and we will want to discuss them and their types. For example, the length of a string is a function. It accepts an argument of type `string` and return a value of type `integer`. We denote the type of the length function with `string -> integer`.

In general if we have types `S` and `T`, a functions that maps values of type `S` into values of type `T` is denoted by `S -> T`.

#### Pair
We sometimes want to combine to pieces of information. For example, you could keep track of your age. At the moment that would be `("daan", 40)`. Notice that the first part is a value of type `string`. The second part is a value of type `integer`. The pair has type `(string, integer)`.

For types `S` and `T`, the type of a pair of these values is denoted by `(S, T)`.

#### List
Sometimes we want discuss lists of things. An example would be a list of the names of your friends. The type of a list of `string`s is denoted by `[string]`.

We can generalize that for an arbitrary type `T`. A list of `T`s is denoted by `[T]`.

#### Summary
In order to clearly express our thoughts we introduce types. There is a concrete notation to denote type, which consist of the following rules.

1. We have some basic types like `string`, `integer`, etcetera.
2. For a type variable `T`, we denote lists of `T` as `[T]`.
3. For type variables `S` and `T`, a pair of `S` and `T` is denoted with `(S, T)`.
4. For type variables `S` and `T`, a function is denoted by `S -> T`.

## Exercises
1. Describe the following type in your own words.

```plain
string -> [(integer,string)]
```

2. The function that counts the occurences of a certain character in a string could be denoted by `(string, character) -> integer`. A different view of this function is would be `string -> character -> integer`. This can be read as a function that accepts as argument a value of type `string` and returns a function of type `character -> integer`.

The process is called [_Currying_][wikipedia:currying] after the computer scientist Haskell Curry.

What is the type of the curried function type `(S, T) -> U`?

[wikipedia:type-system]: https://en.wikipedia.org/wiki/Type_system
[wikipedia:currying]: https://en.wikipedia.org/wiki/Currying
[book:tapl]: https://www.cis.upenn.edu/~bcpierce/tapl/