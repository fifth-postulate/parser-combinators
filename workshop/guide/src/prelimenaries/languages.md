# Languages
When we are studying the topic of parsing, it is inevitable to discuss _languages_. There is a large body of work on [_formal languages_][wikipedia:formal-language] and ways to work with them. The reason for this is that programming languages are examples of formal languages. Knowing how to efficiently transform a member of one programming language into instructions for a computer is what a compiler does.

## Definition
For our use case it is enough to have working knowledge about languages. In this workshop we will be a bit vague and define a language as follows.

> A _language_ is any collection of strings.

An element in a language is called a _word_.

Some examples of languages are

* The language consisting of the authors names: `{"daan", "geert"}`.
* The language consisting of one of the authors childrens names: `{"sophie", "robin", "hannah"}`.
* The language of all english words: `{"ape", "avacado", ... }`.
* The language of words solely consisting of any number of `'a'`s: `{"", "a", "aa", "aaa", ... }`.

## Categories
There are languages in all shapes and sizes. Some languages are finite, such as the language consisting of the authors names. Other languages are infinite.

Some languages have a lot of structure which can be captured in a _grammar_, which will be the subject of our next chapter. 

## Exercises

1. Give your own example of a language.
2. It the English language finite or infinite? What about other natural languages?

[wikipedia:formal-language]: https://en.wikipedia.org/wiki/Formal_language
