class Character:
    def __init__(self, target):
        self.target = target
    
    def parse(self, input):
        if input[0] == self.target:
            return [(str(self.target), input[1:])]
        else:
            return []

def character(target):
    return Character(target)

class Consecutive:
    def __init__(self, first, second):
        self.first = first
        self.second = second
    
    def parse(self, input):
        return [([r1, r2], rest) for (r1, intermediate) in self.first.parse(input) for (r2, rest) in self.second.parse(intermediate)]

def and_then(first, second):
    return Consecutive(first, second)


if __name__ == '__main__':
    assert character('A').parse('ABC') == [('A', 'BC')]
    assert character('A').parse('BC') == []
    assert character('A').parse('aBC') == []
    assert character('a').parse('aBC') == [('a', 'BC')]

    A = character('A')
    B = character('B')

    assert and_then(A, B).parse('ABC') == [(['A', 'B'], 'C')]
    assert and_then(A, B).parse('ABCD') == [(['A', 'B'], 'CD')]
    assert and_then(A, B).parse('aBC') == []