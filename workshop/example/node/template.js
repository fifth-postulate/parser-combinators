const assert = require('assert').strict
const Parser = require('./parser')
const Util = require('./util')

function use(result) {
    return function(context) {
        return result[0][0].evaluate(context)
    }
}

class Literal {
    constructor(literal) {
        this.literal = literal
    }

    evaluate(context) {
        return this.literal
    }
}

class Variable {
    constructor(name) {
        this.name = name
    }

    evaluate(context) {
        if (this.name in context) {
            return context[this.name]
        } else {
            throw "expected '" + this.name + "' to be in context"
        }
    }
}

class Concatenation {
    constructor(templates) {
        this.templates = templates
    }

    evaluate(context) {
        return this.templates
            .map(function (template) { return template.evaluate(context) })
            .join('')
    }
}

function template() {
    return Parser.just(Parser.map(Util.greedy(Parser.or(literal(), substitution())), function (templates) {
        return new Concatenation(templates)
    }))
}

function literal() {
    return Parser.map(Util.greedy1(Parser.or(
        escapedCharacter(),
        notAnOpeningBracket()
    )), function (result) { return new Literal(result.join('')) })
}

function escapedCharacter() {
    return Util.oneOf([
        Parser.map(Parser.token("\\\\"), function () { return "\\" }),
        Parser.map(Parser.token("\\{"), function () { return "{" })
    ])
}

function notAnOpeningBracket() {
    return Parser.satisfy(function (c) { return c != '{' })
}

function notAnClosingBracket() {
    return Parser.satisfy(function (c) { return c != '}' })
}

function substitution() {
    return Parser.map(Util.pack(
        openingBracket(),
        identifier(),
        closingBracket()
    ), function (identifier) { return new Variable(identifier) })
}

function identifier() {
    return Parser.map(
        Util.greedy1(notAnClosingBracket()),
        function (result) { return result.join('') }
    )
}

function openingBracket() {
    return Parser.character('{')
}

function closingBracket() {
    return Parser.character('}')
}

context = { 'subject': 'World' }
assert.equal((new Literal('Hello, World!')).evaluate(context), 'Hello, World!')
assert.equal((new Variable('subject')).evaluate(context), 'World')

try {
    (new Variable('missing from context')).evaluate(context)
    assert.fail
} catch (e) {
    assert.equal(e, "expected 'missing from context' to be in context")
}

assert.equal(use(literal()("Hello, World!"))(context), "Hello, World!")
assert.equal(use(literal()("\\\\"))(context), "\\")
assert.equal(use(literal()("\\{"))(context), "{")

assert.equal(use(substitution()("{subject}"))(context), "World")

assert.equal(use(template()("Hello, {subject}!"))(context), "Hello, World!")