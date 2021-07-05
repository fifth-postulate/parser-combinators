# Balanced parenthesis
The next project to grasp is parsing balanced parenthesis. We will despense with the content inside the parenthesis and just focus on the structure of the parenthesis.

## Grammar
Below we will give grammar for balanced parenthesis

```
S := "(" S ")" S | ""
```

Which in plain english is describing balanced parenthesis as either

1. a opening parenthesis, followed by balenced parenthesis, followed by a closing parenthesis, followed by balenced parenthesis.
2. an empty string

The empty string alternative serves as a base case. Without it, we would have no way to stop accepting parenthesis.

## Structure
We would like to define a data structure that reflects the structure of the balanced parenthesis. A [binary tree][wikipedia:tree] fits nicely.

A *binary tree* has two alternatives. It is either a *leaf* that holds not no data. Or it is a *node* that contains two children, who both are trees. They children are called the *left child* and the *right child*. The left child represents the resultant tree from the first `S` in the production, the right child represents the resultant tree from the second `S` in the production.

## Exercises
1. Represent a binary tree in code.
2. Which trees do the following inputs represent: `"()"`, `"(())()"` and `"()(()())()"`.
3. What is the signature of the parser that can parse trees?
4. Create a parser that can parse trees.

### Extra credit
Formally balanced parenthesis are a [Dyck language][wikipedia:dyck-language]. In this project we restricted our language to only use matching pairs of parenthesis. But we could mix and match delimiters. For example, we could be interested in the language of properly matched pairs of parenthesis `()` and brackets `[]`.

How would the grammar change to allow different pairs of delimiters? How would the parser change? Can you generalize the parser of an arbitrary set of matching delimiters?

[wikipedia:tree]: https://en.wikipedia.org/wiki/Binary_tree
[wikipedia:dyck-language]: https://en.wikipedia.org/wiki/Dyck_language 