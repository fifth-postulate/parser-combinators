class Parser:
    def andThen(self, other):
        return Consecutive(self, other)

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
    def __init__(self, first, second):
        self.first = first
        self.second = second
    
    def parse(self, input):
        return [([r1, r2], rest) for (r1, intermediate) in self.first.parse(input) for (r2, rest) in self.second.parse(intermediate)]

if __name__ == '__main__':
    assert character('A').parse('ABC') == [('A', 'BC')]
    assert character('A').parse('BC') == []
    assert character('A').parse('aBC') == []
    assert character('a').parse('aBC') == [('a', 'BC')]

    A = character('A')
    B = character('B')

    assert A.andThen(B).parse('ABC') == [(['A', 'B'], 'C')]
    assert A.andThen(B).parse('ABCD') == [(['A', 'B'], 'CD')]
    assert A.andThen(B).parse('aBC') == []