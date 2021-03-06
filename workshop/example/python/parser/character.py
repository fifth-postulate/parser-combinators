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

if __name__ == '__main__':
    assert character('A').parse('ABC') == [('A', 'BC')]
    assert character('A').parse('BC') == []
    assert character('A').parse('aBC') == []
    assert character('a').parse('aBC') == [('a', 'BC')]