class Parser:
    def orElse(self, *others):
        return OneOf([self] + list(others))

    def andThen(self, *others):
        return Consecutive([self] + list(others))

    def map(self, transform):
        return Map(transform, self)

class Character(Parser):
    def __init__(self, target):
        self.target = target
    
    def parse(self, input):
        if input[0] == self.target:
            return [(str(self.target), input[1:])]
        else:
            return []

def character(target):
    return Character(target)

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
    assert character('A').parse('ABC') == [('A', 'BC')]
    assert character('A').parse('BC') == []
    assert character('A').parse('aBC') == []
    assert character('a').parse('aBC') == [('a', 'BC')]

    assert token('begin').parse('begin') == [('begin', '')]
    assert token('begin').parse('end') == []

    A = character('A')
    B = character('B')
    C = character('C')

    assert A.andThen(B).parse('ABC') == [(['A', 'B'], 'C')]
    assert A.andThen(B).parse('ABCD') == [(['A', 'B'], 'CD')]
    assert A.andThen(B).parse('aBC') == []
    assert A.andThen(B, C).parse('ABC') == [(['A', 'B', 'C'], '')]

    assert A.orElse(B).parse('ABC') == [('A', 'BC')]
    assert A.orElse(B).parse('BAC') == [('B', 'AC')]
    assert A.orElse(B, C).parse('CAB') == [('C', 'AB')]

    transform = lambda c : ord(c)

    assert A.map(transform).parse('ABC') == [(65, 'BC')]
    assert A.map(transform).parse('aBC') == []