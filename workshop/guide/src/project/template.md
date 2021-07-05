# Template Language
Sometimes you want a template that can be specialized with a certain context. For example, the template `"Hello, {subject}!"` evaluated in the context `subject => "World"` should produce `"Hello, World!".

## Grammar
The grammar for this template language is a little subtle. In broad strokes there are two different parts. *Literal text*, like `"Hello, "` and `"!"` from our example. And *substitutions*, like `"{subject}"`. Substitutions are formed by wrapping an identifier with a matching pair of braces.

Literal text seems straightforward. But what if your text contains a literal `{`? This could certainly trip up a naive parser. You can escape the problem, by escaping the `{`. This allows the parser to differentiate between a start of a substitution and a literal opening brace.

Often one uses the backslash `\` to escape the following character. That would become `\{` in our case. If you think this through you will notice a problem with this "solution". How to include a literal `\`? Luckily one can use the same method: escape it!

```
S := (Literal|Substitution)*
Substitution := "{" Variable "}"
Variable := Alpha+
Alpha := 'a'|'b'|'c'|...|'z'|'A'|'B'|...|'Z'
Literal := (Escaped|Allowed)+
Escaped := "\\"|"\{"
Allowed := <any character not an '\' or '{'>
```

## Data Structure
The data structures that are involved in presenting the user with a template is have a common interface. They can be evaluated in a context to produce a string.

Depending on your temperament you could differentiate between the following distinct templates:

1. Literal template
2. Substitution
3. Concatenation of sub-templates

### Literal Template
When a literal template is evaluated, it ignores the context and produces the literal string it represents.

### Substitution
When a substitution is evaluated, it looks up the string associated with the variable in the current context, and produces that result.

### Concatenation
When a concatenation of templates is evaluated, each sub-templates is evaluated, and the produced results are strung together in order.

## Exercises
1. What is the signature of the context? What is a data structure that matches that signature?
2. Create different data structures to match the proposed templates.
3. What is the signature of the template parser?
4. Create the template parser.
