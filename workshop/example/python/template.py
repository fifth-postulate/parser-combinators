from parser import OneOf, character, greedy, greedy1, pack, satisfy, token

def use(templateResult):
    return templateResult[0][0]

def template():
    return greedy(literal().orElse(substitution()))\
        .map(lambda result: Concatenation(result))

def literal():
    return greedy1(escapedCharacter().orElse(notAnOpeningBracket()))\
        .map(lambda result: ''.join(result))\
        .map(lambda verbatim: Literal(verbatim))

def escapedCharacter():
    return OneOf([
        token('\\').map(lambda _: '\\'),
        token('\\{').map(lambda _: '{'),
    ])

def notAnOpeningBracket():
    return satisfy(lambda character: character != '{')

def notAnClosingBracket():
    return satisfy(lambda character: character != '}')

def substitution():
    return pack(openingBracket(), identifier(), closingBracket())\
        .map(lambda identifier: Variable(identifier))

def identifier():
    return greedy1(notAnClosingBracket())\
        .map(lambda result: ''.join(result))\

def openingBracket():
    return character('{')

def closingBracket():
    return character('}')

class Template:
    def __call__(self, context):
        raise NotImplementedError()

class ValueMissingFromContext(Exception):
    pass

class Literal(Template):
    def __init__(self, literal):
        self.literal = literal

    def __call__(self, context):
        return self.literal

class Variable(Template):
    def __init__(self, name):
        self.name = name

    def __call__(self, context):
        if self.name in context:
            return context[self.name]
        else:
            raise ValueMissingFromContext(f'expected \'{self.name}\' to be in context')

class Concatenation(Template):
    def __init__(self, templates):
        self.templates = templates
    
    def __call__(self, context):
        return ''.join([template(context) for template in self.templates])

if __name__ == '__main__':
    context = { 'subject': 'World' }
    assert Literal('Hello, World!')(context) == 'Hello, World!'
    assert Variable('subject')(context) == 'World'

    try:
        Variable('missing from context')(context)
        assert False
    except ValueMissingFromContext as e:
        assert True
    
    assert Concatenation([])(context) == ''
    assert Concatenation([Literal('Hello, '), Variable('subject'), Literal('!')])(context) == 'Hello, World!'

    use(literal()('Hello, World!'))(context) == 'Hello, World!'
    use(literal()('\\\\'))(context) == '\\'
    use(literal()('\\{'))(context) == '{'

    use(substitution()('{subject}'))(context) == 'World'

    use(template()('Hello, {subject}!'))(context) == 'Hello, World!'