# Grammars and EBNF

Before diving in EBNF (Extended Backus-Naur Form), we need to have a simple understanding on grammars.

## Grammars
A grammar is a set of rules a language adheres to. Every language has a grammar, natural languages (e.g. Dutch or
English) but also computer languages (Java, Haskell, etc.). The rules for computer languages are stricter than the
rules for natural languages. In general computer languages don't allow for ambiguities whereas natural languages are
filled with ambiguities.

A grammar for a computer language (from now on just _language_) is normally described using EBNF.

## EBNF
EBNF (_Extended Backus-Naur Form_) is a way to describe a grammar. For example:

```
non zero digit ::= "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
digit ::= "0" | non zero digit ;
natural number ::= non zero digit digit*
integer = "0" | "-"? natural number ;
```

This can be interpreted as follows.
- A **non zero digit** is a _1_ `or` a _2_ `or` a _3_ `or` ... _9_
- A **digit** is a _0_ `or` a **non zero digit**
- A **natural number** is a **non zero digit** `concatenated` with a `repetition` of **digit**s
- An **integer** is a _0_ `or` an `optional` _-_ `concatenated` with a **natural number**

Someone familiar with regular expressions might recognise the following operators:

| Operator          | Meaning               |
|:------------------|:----------------------|
| * (Kleene star)   | 0 or more occurrences |
| + (Kleene cross)  | 1 or more occurrences |
| ?                 | 0 or 1 occurrences    |
| ( ) (parentheses) | grouping              |
| invisible         | concatenation         |


## Exercises
1. Write a grammar for a simple expression e.g. addition only

