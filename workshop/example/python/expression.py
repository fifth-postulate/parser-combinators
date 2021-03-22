from parser import Consecutive, OneOf, character, first, lazy, many1, satisfy, succeed

def expression():
    return first(OneOf([
        Consecutive([
            term(),
            expression_operator(),
            lazy(expression) 
        ]).map(lambda result: result[1](result[0], result[2])),
        term()
    ]))

def expression_operator():
    return first(OneOf([
        character('+').map(lambda _: (lambda l,r: Addition(l,r))),
        character('-').map(lambda _: (lambda l,r: Subtraction(l,r)))
    ]))

def term ():
    return first(OneOf([
        Consecutive([
            factor(),
            term_operator(),
            lazy(term) 
        ]).map(lambda result: result[1](result[0], result[2])),
        factor()
    ]))

def term_operator():
    return first(OneOf([
        character('*').map(lambda _: (lambda l,r: Multiplication(l,r))),
        character('/').map(lambda _: (lambda l,r: Division(l,r)))
    ]))

def factor():
    return OneOf([
        integer(),
        identifier(),
        parenthesized(lazy(expression))
    ])

def parenthesized(parser):
    return pack(character('('), parser, character(')'))

def pack(opening, parser, closing):
    return Consecutive([
        opening,
        parser,
        closing
    ]).map(lambda result: result[1])

def integer():
    return first(many1(digit())).map(lambda result: Constant(int(''.join(result))))

def digit():
    return satisfy(lambda c : c.isdigit())

def identifier():
    return first(many1(alpha())).map(lambda result: Variable(''.join(result)))

def alpha():
    return satisfy(lambda c : c.isalpha())

class Expression:
    def __add__(self, other):
        return Addition(self, other)
    
    def __sub__(self, other):
        return Subtraction(self, other)

    def __mul__(self, other):
        return Multiplication(self, other)

    def __truediv__(self, other):
        return Division(self, other)


class Constant(Expression):
    def __init__(self, value):
        self.value = value
    
    def __eq__(self, other):
        return isinstance(other, Constant) and self.value == other.value
    
    def __repr__(self):
        return f'Constant({self.value})'

class Variable(Expression):
    def __init__(self, name):
        self.name = name

    def __eq__(self, other):
        return isinstance(other, Variable) and self.name == other.name
    
    def __repr__(self):
        return f'Variable(\'{self.name}\')'

class Addition(Expression):
    def __init__(self, left, right):
        self.left = left
        self.right = right
 
    def __eq__(self, other):
        return isinstance(other, Addition) and self.left == other.left and self.right == other.right
    
    def __repr__(self):
        return f'Addition({self.left}, {self.right})'

class Subtraction(Expression):
    def __init__(self, left, right):
        self.left = left
        self.right = right
 
    def __eq__(self, other):
        return isinstance(other, Subtraction) and self.left == other.left and self.right == other.right
    
    def __repr__(self):
        return f'Subtraction({self.left}, {self.right})'

class Multiplication(Expression):
    def __init__(self, left, right):
        self.left = left
        self.right = right
 
    def __eq__(self, other):
        return isinstance(other, Multiplication) and self.left == other.left and self.right == other.right
    
    def __repr__(self):
        return f'Multiplication({self.left}, {self.right})'

class Division(Expression):
    def __init__(self, left, right):
        self.left = left
        self.right = right
 
    def __eq__(self, other):
        return isinstance(other, Division) and self.left == other.left and self.right == other.right
    
    def __repr__(self):
        return f'Division({self.left}, {self.right})'

if __name__ == '__main__':
    assert Constant(37) == Constant(37)
    assert Variable('foo') == Variable('foo')
    assert (Constant(37) + Constant(51)) == Addition(Constant(37), Constant(51))
    assert (Variable('foo') - Constant(37)) == Subtraction(Variable('foo'), Constant(37))
    assert (Constant(37) * Constant(51)) == Multiplication(Constant(37), Constant(51))
    assert (Variable('foo') / Constant(37)) == Division(Variable('foo'), Constant(37))

    assert integer()('37') == [(Constant(37), '')]
    assert identifier()('foo') == [(Variable('foo'), '')]

    assert factor()('37') == [(Constant(37), '')]
    assert factor()('foo') == [(Variable('foo'), '')]

    assert term()('37') == [(Constant(37), '')]
    assert term()('37*foo') == [(Multiplication(Constant(37), Variable('foo')), '')]
    assert term()('bar/51') == [(Division(Variable('bar'), Constant(51)), '')]

    assert expression()('37+foo*51') == [(Addition(Constant(37), Multiplication(Variable('foo'), Constant(51))), '')]
