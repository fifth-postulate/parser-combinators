class Parser:
    def andThen(self, *others):
        return Consecutive([self] + list(others))

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

class Consecutive(Parser):
    def __init__(self, parsers):
        self.parsers = parsers
    
    def parse(self, input):
        return parse_with(self.parsers, input)
        return [([r1, r2], rest) for (r1, intermediate) in self.first.parse(input) for (r2, rest) in self.second.parse(intermediate)]

def parse_with(parsers, input):
    if not parsers:
        return [([], input)]
    else:
        return [([r1] + results, remaining) for (r1, intermediate) in parsers[0].parse(input) for (results, remaining) in parse_with(parsers[1:], intermediate)]

if __name__ == '__main__':
    assert character('A').parse('ABC') == [('A', 'BC')]
    assert character('A').parse('BC') == []
    assert character('A').parse('aBC') == []
    assert character('a').parse('aBC') == [('a', 'BC')]

    A = character('A')
    B = character('B')
    C = character('C')

    assert A.andThen(B).parse('ABC') == [(['A', 'B'], 'C')]
    assert A.andThen(B).parse('ABCD') == [(['A', 'B'], 'CD')]
    assert A.andThen(B).parse('aBC') == []
    assert A.andThen(B, C).parse('ABC') == [(['A', 'B', 'C'], '')]