# Succeed and Friends
So we have established a signature for our parser

```
string -> [(T, string)]
```

In other words: a parser is a function that accepts a string and returns a list of pairs. Each pair in the list is a combination of a value of type `T` and a string, which is the remaining input.

What would the simplest parser be?

## `succeed`
One of the simplest parser would not bother with the input and instead return a value we provide.

Thinking hard about the signature we could come up with

```
T -> string -> [(T, string)]
```

Let's break this signature down. There are some arrows involved so this is a function. The part after the first arrow is the same as the signature for our parser.

So this is a function that accepts an argument of type `T` and returns the parser.

Below you can find some examples of how we would use succeed

| input      | *succeed(37)*      |
|------------|--------------------|
| "whatever" | [(37, "whatever")] |
| "input"    | [(37, "input")]    |
| "ABC"      | [(37, "ABC")]      |


With succeed under our belt, we can go on to an other simple parser. One could argue that it is even simpler then `succeed`.

## `fail`
Some parsers fail to turn the input into a meaningfull structure. `fail` is the quintessential example. It fails on any input, whatever that is.

Here are some examples.

| input      | *fail()* |
|------------|----------|
| "whatever" | []       |
| "input     | []       |
| "ABC"      | []       |

## Why these parsers?
The `succeed` and `fail` parser seem very simple. Certainly they can not have practical use, other then introducing you to the idea of parsers.

Although `succeed` and `fail` are simple parsers, the are powerful none the less. It can seem that these parsers would not amount to anything, but within a few chapters they allow you to wield tremendous power.

So don't dismiss them just yet, they will come in handy rather sooner then later.

## Exercises
1. Provide your own example to cement your understanding of `succeed`. Discuss your example with somebody.
2. Implement the `succeed` function in your language of choice. You will have to choose how to represent functions.
3. Make sure you are confident your implementation works as expected. You could write tests, use a read-eval-print-loop (REPL) or any other means to check.
4. What is the type of the `fail` function?
5. Implement `fail` and test your implementation. 
