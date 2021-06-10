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

consecutive = module.exports.consecutive = function consecutive(parsers) {
    if (parsers.length == 0) {
        return Parser.succeed([])
    } else {
        return Parser.map(Parser.and(parsers[0], consecutive(parsers.slice(1))), function(result){
            return [result[0]].concat(result[1])
        })
    }
}

A = Parser.character('A')
B = Parser.character('B')
C = Parser.character('C')

assert.deepEqual(first(many(A))("AAAB"), [[['A', 'A', 'A'], "B"]])
assert.deepEqual(greedy(A)("AAAB"), [[['A', 'A', 'A'], "B"]])
assert.deepEqual(greedy1(A)("B"), [])

assert.deepEqual(consecutive([A, B, C])("ABCD"), [[['A', 'B', 'C'], "D"]])
