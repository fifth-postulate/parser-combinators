# Parse this and then that
With a basic parser under our belt, we start looking at a combinator. A combinator is a function that *combines* simpler things into a more complex thing.

What we are going to combine in this chapter is two parsers with the intent of parsing with the first parser **and then** parse the remaining input with the second parser.