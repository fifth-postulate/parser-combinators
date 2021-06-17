const assert = require('assert').strict
const Parser = require('./parser')
const Util = require('./util')

class Expression {
    plus(other) {
        return new Addition(this, other)
    }

    minus(other) {
        return new Subtraction(this, other)
    }

    times(other) {
        return new Multiplication(this, other)
    }

    divideBy(other) {
        return new Division(this, other)
    }
}


class Constant extends Expression {
    constructor(value) {
        super()
        this.value = value
    }

    equals(other) {
        return other instanceof Constant && this.value === other.value
    }

    representation() {
        return 'Constant(' + this.value + ')'
    }
}

class Variable extends Expression {
    constructor(name) {
        super()
        this.name = name
    }

    equals(other) {
        return other instanceof Variable && this.name === other.name
    }

    representation() {
        return 'Variable(' + this.name + ')'
    }
}

class Addition extends Expression {
    constructor(left, right) {
        super()
        this.left = left
        this.right = right
    }

    equals(other) {
        return other instanceof Addition &&
            this.left.equals(other.left) &&
            this.right.equals(other.right)
    }

    representation() {
        return 'Addition(' + this.left + ',' + this.right + ')'
    }
}

class Subtraction extends Expression {
    constructor(left, right) {
        super()
        this.left = left
        this.right = right
    }

    equals(other) {
        return other instanceof Subtraction &&
            this.left.equals(other.left) &&
            this.right.equals(other.right)
    }

    representation() {
        return 'Subtraction(' + this.left + ',' + this.right + ')'
    }
}

class Multiplication extends Expression {
    constructor(left, right) {
        super()
        this.left = left
        this.right = right
    }

    equals(other) {
        return other instanceof Multiplication &&
            this.left.equals(other.left) &&
            this.right.equals(other.right)
    }

    representation() {
        return 'Multiplication(' + this.left + ',' + this.right + ')'
    }
}

class Division extends Expression {
    constructor(left, right) {
        super()
        this.left = left
        this.right = right
    }

    equals(other) {
        return other instanceof Division &&
            this.left.equals(other.left) &&
            this.right.equals(other.right)
    }

    representation() {
        return 'Division(' + this.left + ',' + this.right + ')'
    }
}

function expression() {
    return Util.first(Util.oneOf([
        Parser.map(Util.consecutive([
            term(),
            expressionOperator(),
            Parser.lazy(expression)
        ]), function (result) { return result[1](result[0], result[2]) }),
        term()
    ]))
}

function expressionOperator() {
    return Util.oneOf([
        Parser.map(Parser.character('+'), function () { return function (l, r) { return new Addition(l, r) } }),
        Parser.map(Parser.character('-'), function () { return function (l, r) { return new Subtraction(l, r) } }),
    ])
}


function term() {
    return Util.first(Util.oneOf([
        Parser.map(Util.consecutive([
            factor(),
            termOperator(),
            lazy(term)
        ]), function (result) { return result[1](result[0], result[2]) }),
        factor()
    ]))
}

function termOperator() {
    return Util.oneOf([
        Parser.map(Parser.character('*'), function () { return function (l, r) { return new Multiplication(l, r) } }),
        Parser.map(Parser.character('/'), function () { return function (l, r) { return new Division(l, r) } }),
    ])
}

function factor() {
    return Util.oneOf([
        integer(),
        identifier(),
        Util.parenthesized(Parser.lazy(factor))
    ])
}

function integer() {
    return Parser.map(Util.greedy1(digit()), function (result) {
        return new Constant(parseInt(result.join('')))
    })
}

function digit() {
    return Parser.satisfy(function (c) { return '0' <= c && c <= '9' })
}

function identifier() {
    return Parser.map(Util.greedy1(alpha()), function (result) {
        return new Variable(result.join(''))
    })
}

function alpha() {
    return Parser.satisfy(function (c) { return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') })
}

assert.ok((new Constant(37)).equals(new Constant(37)))
assert.ok((new Variable('foo')).equals(new Variable('foo')))
assert.ok((new Constant(37).plus(new Constant(51))).equals(new Addition(new Constant(37), new Constant(51))))
assert.ok((new Variable('foo').minus(new Constant(37))).equals(new Subtraction(new Variable('foo'), new Constant(37))))
assert.ok((new Constant(37).times(new Constant(51))).equals(new Multiplication(new Constant(37), new Constant(51))))
assert.ok((new Variable('foo').divideBy(new Constant(37))).equals(new Division(new Variable('foo'), new Constant(37))))

function test(parser, input, expectation) {
    result = parser(input)
    assert.equal(result[0][1], '')
    assert.ok(expectation.equals(result[0][0]))
}

test(integer(), '37', new Constant(37))
test(identifier(), 'foo', new Variable('foo'))

test(factor(), '37', new Constant(37))
test(factor(), 'foo', new Variable('foo'))
test(factor(), '(37)', new Constant(37))

test(term(), '37', new Constant(37))
test(term(), '37*foo', new Multiplication(new Constant(37), new Variable('foo')))
test(term(), 'bar/51', new Division(new Variable('bar'), new Constant(51)))

test(expression(), '37+foo*51', new Addition(new Constant(37), new Multiplication(new Variable('foo'), new Constant(51))))