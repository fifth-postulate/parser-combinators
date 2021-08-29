# Balanced parentheses
The next project to grasp is parsing balanced parentheses. We will dispense with the content inside the parentheses and just focus on the structure of the parentheses.

## Grammar
Below we will give a grammar for balanced parentheses:

```
S := "(" S ")" S | ""
```

Which in plain english is describing balanced parentheses as either

1. a opening parenthesis, followed by balanced parentheses, followed by a closing parenthesis, followed by balanced parentheses.
2. an empty string

The empty string alternative serves as a base case. Without it, we would have no way to stop accepting parentheses.

## Structure
We would like to define a data structure that reflects the structure of the balanced parentheses. A [binary tree][wikipedia:tree] fits nicely.

A *binary tree* has two alternatives. It is either a *leaf* that holds not no data. Or it is a *node* that contains two children, who both are trees. The children are called the *left child* and the *right child*. The left child represents the resultant tree from the first `S` in the production, the right child represents the resultant tree from the second `S` in the production.

## Exercises
1. Represent a binary tree in code.
2. Which trees do the following inputs represent: `"()"`, `"(())()"` and `"()(()())()"`?
3. What is the signature of the parser that can parse trees?
4. Create a parser that can parse trees.

### Extra credit
Formally balanced parentheses are a [Dyck language][wikipedia:dyck-language]. In this project we restricted our language to only use matching pairs of parentheses. But we could mix and match delimiters. For example, we could be interested in the language of properly matched pairs of parentheses `()` and brackets `[]`.

How would the grammar change to allow different pairs of delimiters? How would the parser change? Can you generalize the parser of an arbitrary set of matching delimiters?

[wikipedia:tree]: https://en.wikipedia.org/wiki/Binary_tree
[wikipedia:dyck-language]: https://en.wikipedia.org/wiki/Dyck_language 