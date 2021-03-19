from parser import Consecutive, character, lazy, epsilon, just, succeed

class Tree:
    pass

class Nil(Tree):
    def __eq__(self, other):
        return isinstance(other, Nil)
    
    def __repr__(self):
        return 'Nil()'

class Bin(Tree):
    def __init__(self, first, second):
        self.first = first
        self.second = second
    
    def __eq__(self, other):
        return isinstance(other, Bin) and self.first == other.first and self.second == other.second

    def __repr__(self):
        return f'Bin({self.first},{self.second})'

def foldparens(combine, initial):
    parser = Consecutive([
        character('('),
        lazy(lambda : parser),
        character(')'),
        lazy(lambda : parser)
    ]).map(combine).orElse(succeed(initial))
    return parser


def parens():
    return foldparens(lambda result: Bin(result[1], result[3]), Nil())

def nesting():
    return foldparens(lambda result: max(1 + result[1], result[3]), 0)

if __name__ == '__main__':
    assert Nil() == Nil()
    assert Bin(Nil(), Bin(Nil(), Nil())) == Bin(Nil(), Bin(Nil(), Nil()))
    assert just(parens())('()(())') == [(Bin(Nil(),Bin(Bin(Nil(),Nil()),Nil())), '')]

    depth = just(nesting())
    assert depth('') == [(0, '')]
    assert depth('()') == [(1, '')]
    assert depth('()(())') == [(2, '')]