# Whitespace
When parsing certain input, we are often not interested in the whitespace that is present. For example, the following expression should all result in the same data structure.

* `5*2+1`
* `5*2 + 1`
* `5 * 2 + 1`
* `  5  *    2  +   1`

## What is whitespace?
Depending on your use case whitespace could mean different things. It could be just a space, just a tab, just a newline or any combination of the mentioned, and possibly other, characters.

But once a choice is made, one could create a parser for it. We will call this parser `whitespace`.

## Ignore whitespace prefix
If whitespace is not important for the input that you are parsing, it can be ignored. A usefull parser combinator in this case is the `sp` parser combinator. It accepts a parser `p` and parses everything that `p` parses, even if it is preceded by any amount of whitespace.

| *input* | *character('A')* | *sp(character('A'))* |
|---------|------------------|----------------------|
| "ABC"   | [('A', "BC")]    | [('A', "BC")]        |
| " ABC"  | []               | [('A', "BC")]        |
| "  ABC" | []               | [('A', "BC")]        |

The above table provides an example of the `sp` parser combinator. Where `character('A')` would fail if the string starts with any number of whitespace, the `sp(character('A'))` ignores the whitespace prefix and returns the wanted result.

## Exercises
1. What is the signature of the `whitespace` parser?
2. Implement the `whitespace` parser.
3. What is the signature of the `sp` parser combinator?
4. Implement the `sp` parser combinator.