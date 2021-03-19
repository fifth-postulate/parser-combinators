from parser import Consecutive, character, lazy, epsilon, just

class Tree:
    pass

class Nil(Tree):
    def __eq__(self, other):
        return isinstance(other, Nil)
    
    def __repr__(self):
        return 'Nil'

class Bin(Tree):
    def __init__(self, first, second):
        self.first = first
        self.second = second
    
    def __eq__(self, other):
        return isinstance(other, Bin) and self.first == other.first and self.second == other.second

    def __repr__(self):
        return f'Bin({self.first},{self.second})'

def parens():
    return Consecutive([
        character('('),
        lazy(parens),
        character(')'),
        lazy(parens)
    ]).map(lambda result: Bin(result[1], result[3])).orElse(epsilon().map(lambda _: Nil()))

if __name__ == '__main__':
    assert Nil() == Nil()
    assert Bin(Nil(), Bin(Nil(), Nil())) == Bin(Nil(), Bin(Nil(), Nil()))
    result = just(parens())('()(())')
    print(result)