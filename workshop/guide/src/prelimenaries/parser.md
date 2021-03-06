# What is a Parser
Before we start out creating a parser combinator framework, it is good to consider the question

> What is a parser?

In this workshop a parser is "function" that takes as input a `string` that describes some structure, and returns representation of that structure in memory.

For example, say that we are handed the string `"37"`. If we are told that the string contains a description of an integer, we might wan to use a parser to turn that description into an actual integer. I.e. the integer `37`.

There is a problem though. What if we are told that the input contains a description of an integer in base 10 and we are handed the string `"teapot"`? Even with the best will in the world, it is impossible for a parser to turn `"teapot"` into an integer in a meaningfull way.

So we need a way for parsers to signal that they could fail.

## Signature
We need a way to distuingish between a parser that fails and a parser that succeeds. We will use a list for that. If a parser fails we return an empty list. If a parser succeeds we return a non-empty list. But what should we return in the list.

### First thought
As a first thought you might guess that we should return the structure for which the input is a description. This would work in the example above. Unfortunatly, that choice is not very composable.

For this to make sense we have to understand our overal goal. What we would like is to create complex parsers that our build up from simpler parsers. But how would you combine simpler parser if your parser returns just the success or failure of parsing a certain structure? The complex parser would than have to keep track of how many characters are consumed.

Keeping track of the which part of the input is consumed is tedious, especially if it has to be done anew for each parser we create.

### Second thought
An other option is to not only return the structure, but also the part of the input that is unprocessed. For example, say that we an have an input that represents a tuple of a age and an name. I.e. `"40 daan"`. We could use our parser that parses an integer for the age part. If it would only return the integer, it would not be easy to combine other parsers.

Instead if the integer parser would return `[(40, " daan")]`, other parser could pick up where the integer parser left off.

### Final Form
So the final form of our parser is as follows. It is a function that accepts a `string` and returns a list of pairs. The first component of the pair is the structure that the parser has parsed, the second component of the pair is the remaining part of the input.

The above sentence can be succintly described by the type for a parser

> string -> [(T, string)]

## Caveats
There are a lot of caveats in the above "definition" and we will discuss some of them here. Some others are discussed in the exercises. And there are probably some that we aren't even aware of. So don't hesitate to come and discuss them with us.

### "Function"
We have put the term *function* in quotes. The reason for this is that we are not in control of which programming language you use. You are free to use any language you like.

Some language have functions as first class citizen. You are in luck if you are using such a language. You could use actual functions as parsers.

If the language of your choice does not have good support for functions, don't worry. You could mimic the technique in this workshop. It might be a bit verbose at some points, but the ideas are still worthwhile.

### Returning List
We opted to have a List as return type. This is a choice and you could take a different route here.

The choice isn't without thought. One of the first descriptions of parser combinators also used list in their return type. This allows to signal failure to parse with an empty list and to signal success with a singleton list.

But other possibilities are open to us as well. For example, it allows one to return multiple succesfull results. This is helpfull when the grammar is ambiguous. Instead of working hard to remove the ambiguity in the grammar or in the implementation, we just return all possible parsings.

### Error information
A good parser would also provide hints on what went wrong. The minimum of good error reporting is the row and column information of where the parser got stuck.

We will not concentrate on error reporting in this workshop. There are some hints in the exercises how one could handle this.

## Exercises
1. What is the smallest base in which `"teapot"` could be a description of a valid integer? Assume that 'a' corresponds with 10, 'b' with 11 etcetera.
2. If your language of choice does not support first class functions, how could you remedy that? Discuss your ideas with a participant of the guides.
3. How would you model the success and failure states of a parser?
4. How would you enrich the return type to fascilitate error reporting?