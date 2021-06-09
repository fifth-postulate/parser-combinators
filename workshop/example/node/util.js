const assert = require('assert').strict;
const Parser = require('./parser')

first = module.exports.first = function first(p) {
    return function(input){
        return p(input).slice(0, 1)
    }
}

greedy = module.exports.greedy = function greedy(p) {
    return first(Parser.many(p))
}

greedy1 = module.exports.greedy1 = function greedy1(p) {
    return first(Parser.many1(p))
}

A = Parser.character('A')

assert.deepEqual(first(many(A))("AAAB"), [[['A', 'A', 'A'], "B"]])
assert.deepEqual(greedy(A)("AAAB"), [[['A', 'A', 'A'], "B"]])
assert.deepEqual(greedy1(A)("B"), [])
