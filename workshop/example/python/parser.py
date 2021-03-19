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
        if len(input) > 0 and self.predicate(input[0]):
            return [(str(input[0]), input[1:])]
        else:
            return []

def satisfy(predicate):
    return Predicate(predicate)

def character(target):
    return satisfy(lambda c: c == target)

class Succeed(Parser):
    def __init__(self, result):
        self.result = result

    def parse(self, input):
        return [(self.result, input)]

def succeed(result):
    return Succeed(result) 

def epsilon():
    return succeed(())

class Fail(Parser):
    def parse(self, input):
        return []

def fail():
    return Fail() 

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

class Sp(Parser):
    def __init__(self, parser):
        self.parser = parser
    
    def parse(self, input):
        return self.parser(input.lstrip())

def sp(parser):
    return Sp(parser)

class Just(Parser):
    def __init__(self, parser):
        self.parser = parser

    def parse(self, input):
        return [(result, remaining) for (result, remaining) in self.parser(input) if remaining == '']

def just(parser):
    return Just(parser)

class Map(Parser):
    def __init__(self, transform, parser):
        self.transform = transform
        self.parser = parser

    def parse(self, input):
        return [(self.transform(result), remaining) for (result, remaining) in self.parser.parse(input)]

class Lazy(Parser):
    def __init__(self, producer):
        self.producer = producer
    
    def parse(self, input):
        parser = self.producer()
        return parser(input)

def lazy(producer):
    return Lazy(producer)

def many(p):
    return Consecutive([
        p,
        lazy(lambda : many(p))
    ]).map(lambda result: [result[0]] + result[1]).orElse(succeed([]))

def many1(p):
    return Consecutive([p,many(p)]).map(lambda result: [result[0]] + result[1])

class First(Parser):
    def __init__(self, parser):
        self.parser = parser
    
    def parse(self, input):
        results = self.parser(input)
        if len(results) > 0:
            return [results[0]]
        else:
            return []

def first(parser):
    return First(parser)

def greedy(parser):
    return first(many(parser))

def greedy1(parser):
    return first(many1(parser))

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

    assert sp(A)('     ABC') == [('A', 'BC')]
    assert just(A)('A') == [('A', '')]
    assert just(A)('AB') == []

    transform = lambda c : ord(c)

    assert A.map(transform)('ABC') == [(65, 'BC')]
    assert A.map(transform)('aBC') == []

    assert lazy(lambda : A)('ABC') == [('A', 'BC')]

    assert many(A)('AAAB') == [(['A', 'A', 'A'], 'B'), (['A', 'A'], 'AB'), (['A'], 'AAB'), ([], 'AAAB')]
    assert many1(A)('AAAB') == [(['A', 'A', 'A'], 'B'), (['A', 'A'], 'AB'), (['A'], 'AAB')]

    assert first(many(A))('AAAB') == [(['A', 'A', 'A'], 'B')]
    assert greedy(A)('AAAB') == [(['A', 'A', 'A'], 'B')]
    assert greedy1(A)('B') == []