const assert = require('assert').strict
const Parser = require('./parser')
const Util = require('./util')

class Nil {
    constructor() {}

    representation() {
        return "Nil()"
    }

    equals(other) {
        return other instanceof Nil
    }
}

class Bin {
    constructor(left, right) {
        this.left = left
        this.right = right
    }

    representation() {
        return "Bin(" + 
            this.left.representation() + 
            "," + 
            this.right.representation() +
            ")"
    }

    equals(other) {
        if (other instanceof Bin) {
            return this.left.equals(other.left) && this.right.equals(other.right)
        }
        return false
    }
}

function foldparens(combine, initial){
    const parser = Parser.or(
        Parser.map(Util.consecutive([
            Parser.character('('),
            Parser.lazy(function(){ return parser }),
            Parser.character(')'),
            Parser.lazy(function(){ return parser})
        ]), combine),
        Parser.succeed(initial)
    )
    return parser
}

const parens = foldparens(
    function(result){return new Bin(result[1],result[3])},
    new Nil()
)

const nesting = foldparens(
    function(result){return Math.max(1 + result[1], result[3])},
    0
)

assert.ok(new Nil().equals(new Nil()))
assert.ok(
    new Bin(new Nil(), new Bin(new Nil(), new Nil())).equals(
    new Bin(new Nil(), new Bin(new Nil(), new Nil()))))
const result = Parser.just(parens)("()(())")
assert.ok(
    new Bin(new Nil(), new Bin(new Bin(new Nil(), new Nil()), new Nil())).equals(
    result[0][0]))
