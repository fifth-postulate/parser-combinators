const assert = require('assert').strict;

fail = module.exports.fail = function fail() {
    return function(_) {
        return []
    }
}

succeed = module.exports.succeed = function succeed(value) {
    return function(input) {
        return [[value, input]]
    }
}

satisfy = module.exports.satisfy = function satisfy(predicate) {
    return function(input) {
        if (input.length > 0 && predicate(input[0])) {
            return [[input[0], input.slice(1)]]
        } else {
            return []
        }
    }
}

character = module.exports.character = function character(target) {
    return satisfy(function(character) { return character == target })
}

token = module.exports.token = function token(target) {
    return function(input) {
        if (input.startsWith(target)) {
            return [[target, input.slice(target.length)]]
        } else {
            return []
        }
    }
}

or = module.exports.or = function or(p, q) {
    return function(input) {
        return p(input).concat(q(input))
    }
}

and = module.exports.and = function and(p, q) {
    return function(input) {
        var result = []
        p(input).forEach(function(first){
            q(first[1]).forEach(function(second){
                result.push([[first[0], second[0]], second[1]])
            })
        })
        return result
    }
}

just = module.exports.just = function just(p) {
    return function(input) {
        return p(input).filter(function(result){
            return result[1] === ""
        })
    }
}

map = module.exports.map = function map(p, transform) {
    return function(input) {
        return p(input).map(function(result){
            return [transform(result[0]), result[1]]
        })
    }
}

lazy = module.exports.lazy = function lazy(promise) {
    return function(input) {
        return promise()(input)
    }
}

many = module.exports.many = function many(p) {
    return or(
            map(and(p, lazy(function(){ return many(p) })), function(result){
                return [result[0]].concat(result[1])
            }),
            succeed([]))
}

many1 = module.exports.many1 = function many1(p) {
    return map(and(p, many(p)),
        function(result){
            return [result[0]].concat(result[1])
        })
}

assert.deepEqual(succeed(37)("Hello, World!"), [[37, "Hello, World!"]])
assert.deepEqual(fail()("Hello, World!"), [])

assert.deepEqual(character('A')("ABC"), [['A', "BC"]])
assert.deepEqual(character('A')("BC"), [])
assert.deepEqual(character('A')("aBC"), [])
assert.deepEqual(character('a')("aBC"), [['a', "BC"]])

assert.deepEqual(token("begin")("begin"), [["begin", ""]])
assert.deepEqual(token("begin")("end"), [])

const A = character('A')
const B = character('B')
const C = character('C')

assert.deepEqual(or(A, B)("ABC"), [['A', "BC"]])
assert.deepEqual(or(A, B)("BAC"), [['B', "AC"]])
assert.deepEqual(or(A, B)("aBC"), [])

assert.deepEqual(and(A, B)("ABC"), [[['A', 'B'], "C"]])
assert.deepEqual(and(A, B)("ABCD"), [[['A', 'B'], "CD"]])
assert.deepEqual(and(A, B)("aBC"), [])

assert.deepEqual(just(A)("A"), [['A', ""]])
assert.deepEqual(just(A)("AB"), [])

transform = function(c) {return c.charCodeAt(0) }

assert.deepEqual(map(A, transform)("ABC"), [[65, "BC"]])
assert.deepEqual(map(A, transform)("aBC"), [])

assert.deepEqual(lazy(function(){return A})("ABC"), [['A', "BC"]])

assert.deepEqual(many(A)("AAAB"), [[['A', 'A', 'A'], "B"], [['A', 'A'], "AB"], [['A'], "AAB"], [[], "AAAB"]])
assert.deepEqual(many1(A)("AAAB"), [[['A', 'A', 'A'], "B"], [['A', 'A'], "AB"], [['A'], "AAB"]])
