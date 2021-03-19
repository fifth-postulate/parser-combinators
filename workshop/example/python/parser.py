class Parser:
    def orElse(self, *others):
        return OneOf([self] + list(others))

    def andThen(self, *others):
        return Consecutive([self] + list(others))

    def map(self, transform):
        return Map(transform, self)

    def parse(self, input):
        raise NotImplementedError()

    def __call__(self, input):
        return self.parse(input)    

class Character(Parser):
    def __init__(self, target):
        self.target = target
    
    def parse(self, input):
        if input[0] == self.target:
            return [(str(self.target), input[1:])]
        else:
            return []

class Token(Parser):
    def __init__(self, target):
        self.target = target
    
    def parse(self, input):
        if input.startswith(self.target):
            return [(str(self.target), input[len(self.target):])]
        else:
            return []

def token(target):
    return Token(target)

class Predicate(Parser):
    def __init__(self, predicate):
       self.predicate = predicate

    def parse(self, input):
        if self.predicate(input[0]):
            return [(str(input[0]), input[1:])]
        else:
            return []

def satisfy(predicate):
    return Predicate(predicate)

def character(target):
    return satisfy(lambda c: c == target)

def succeed(result):
    return lambda input: [(result, input)]

def epsilon():
    return succeed(())

def fail():
    return lambda input: []

class OneOf(Parser):
    def __init__(self, parsers):
        self.parsers = parsers
    
    def parse(self, input):
        return [(result, remaining) for parser in self.parsers for (result, remaining) in parser.parse(input)]

class Consecutive(Parser):
    def __init__(self, parsers):
        self.parsers = parsers
    
    def parse(self, input):
        return parse_with(self.parsers, input)

def parse_with(parsers, input):
    if not parsers:
        return [([], input)]
    else:
        return [([r1] + results, remaining) for (r1, intermediate) in parsers[0].parse(input) for (results, remaining) in parse_with(parsers[1:], intermediate)]

class Map(Parser):
    def __init__(self, transform, parser):
        self.transform = transform
        self.parser = parser

    def parse(self, input):
        return [(self.transform(result), remaining) for (result, remaining) in self.parser.parse(input)]

if __name__ == '__main__':
    assert character('A')('ABC') == [('A', 'BC')]
    assert character('A')('BC') == []
    assert character('A')('aBC') == []
    assert character('a')('aBC') == [('a', 'BC')]

    assert token('begin')('begin') == [('begin', '')]
    assert token('begin')('end') == []

    assert epsilon()('Hello, World!') == [((), 'Hello, World!')]
    assert succeed(37)('Hello, World!') == [(37, 'Hello, World!')]
    assert fail()('Hello, World!') == []

    A = character('A')
    B = character('B')
    C = character('C')

    assert A.andThen(B)('ABC') == [(['A', 'B'], 'C')]
    assert A.andThen(B)('ABCD') == [(['A', 'B'], 'CD')]
    assert A.andThen(B)('aBC') == []
    assert A.andThen(B, C)('ABC') == [(['A', 'B', 'C'], '')]

    assert A.orElse(B)('ABC') == [('A', 'BC')]
    assert A.orElse(B)('BAC') == [('B', 'AC')]
    assert A.orElse(B, C)('CAB') == [('C', 'AB')]

    transform = lambda c : ord(c)

    assert A.map(transform)('ABC') == [(65, 'BC')]
    assert A.map(transform)('aBC') == []