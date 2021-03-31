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
    
    assert Concatenation([Literal('Hello, '), Variable('subject'), Literal('!')])(context) == 'Hello, World!'