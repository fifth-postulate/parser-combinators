layout: true
class: middle, center

---

# Parsing 

---
name: how-to

> ## How to validate `X` with regular expressions?

---
template: how-to

## You Don't

---
template: how-to

![A cat laying on a keyboard](image/cat-keyboard.jpeg)

---

![xkcd on regular expressions](https://imgs.xkcd.com/comics/regular_expressions.png)

---

## XY Problem

---

## Parsing

> Extracting meaning from structured input

---

```java
interface Parser {
    ?
}
```

---

```java
interface Parser {
    ? parse(? input)
}
```

---

```java
interface Parser {
    ? parse(String input)
}
```

---

```java
interface Parser {
    List<?> parse(String input)
}
```

---

```java
interface Parser<T> {
    List<Pair<T, String>> parse(String input)
}
```

--

```java
record Pair<L, R>(L first, R second) {}
```

---

```java
public record Fail<T>() implements Parser<T> {
    @Override
    public List<Pair<T, String>> parse(String input) {
        return Collections.emptyList();
    }

    public static <U> Parser<U> fail() {
        return new Fail<>();
    }
}
```

---

```java
public record Succeed<A>(A result) implements Parser<A> {
    @Override
    public List<Pair<A, String>> parse(String input) {
        return Collections.singletonList(pair(result, input));
    }

    public static <U> Parser<U> succeed(U result) {
        return new Succeed<>(result);
    }
}
```

---

```java
public record Character(java.lang.Character target) implements Parser<java.lang.Character> {
    @Override
    public List<Pair<java.lang.Character, String>> parse(String input) {
        if (input.startsWith(String.valueOf(target))) {
            return Collections.singletonList(
                pair(target, input.substring(1))
            );
        } else {
            return Collections.emptyList();
        }
    }

    public static Parser<java.lang.Character> character(char target) {
        return new Character(target);
    }
}
```

---

```java
public record Token(String token) implements Parser<String> {
    @Override
    public List<Pair<String, String>> parse(String input) {
        if (input.startsWith(token)) {
            return Collections.singletonList(
                pair(token, input.substring(token.length()))
            );
        } else {
            return Collections.emptyList();
        }
    }

    public static Parser<String> token(String token) {
        return new Token(token);
    }
}
```

---

```java
public record Predicate(
        java.util.function.Predicate<java.lang.Character> predicate
) implements Parser<java.lang.Character> {
    @Override
    public List<Pair<java.lang.Character, String>> parse(String input) {
        if (input.length() > 0) {
            char[] characters = new char[1];
            input.getChars(0, 1, characters, 0);
            char character = characters[0];
            if (predicate.test(character)) {
                return Collections.singletonList(pair(characters[0], input.substring(1)));
            }
        }
        return Collections.emptyList();
    }

    public static Parser<java.lang.Character> predicate(java.util.function.Predicate<java.lang.Character> predicate) {
        return new Predicate(predicate);
    }
}
```

---

## Base

---

## Combinators

> Combining simple parsers into more complex parser

---

```java
public record Or<T>(Parser<T> left, Parser<T> right) implements Parser<T> {
    @Override
    public List<Pair<T, String>> parse(String input) {
        List<Pair<T, String>> result = new ArrayList<>();
        result.addAll(left.parse(input));
        result.addAll(right.parse(input));
        return result;
    }

    public static <U> Parser<U> or(Parser<U> left, Parser<U> right) {
        return new Or<>(left, right);
    }
}
```

---

```java
public record And<L, R>(Parser<L> first, Parser<R> second) implements Parser<Pair<L, R>> {
    @Override
    public List<Pair<Pair<L, R>, String>> parse(String input) {
        return first.parse(input)
                .stream()
                .flatMap(this::proceedWithSecond)
                .collect(Collectors.toList());
    }

    private Stream<Pair<Pair<L, R>, String>> proceedWithSecond(
        Pair<L, String> intermediate
    ) {
        return second.parse(intermediate.second())
                .stream()
                .map(q -> q.mapFirst(
                    r -> pair(intermediate.first(), r)
                ));
    }

    public static <U, V> Parser<Pair<U, V>> and(Parser<U> first, Parser<V> second) {
        return new And<>(first, second);
    }
}
```

---

```java
public record Map<U, V>(Parser<U> parser, Function<U, V> transform) implements Parser<V> {
    @Override
    public List<Pair<V, String>> parse(String input) {
        return parser.parse(input)
                .stream()
                .map(q -> q.mapFirst(transform))
                .collect(Collectors.toList());
    }

    public static <S, T> Parser<T> map(Parser<S> parser, Function<S, T> transform) {
        return new Map<>(parser, transform);
    }
}
```

---

## What does this parser parse?

```java
    public <T> ? mystery(Parser<T> parser) {
        return or(
                map(
                    and(parser, mystery(parser)),
                    Mystery::prepend
                ),
                succeed(Collections.emptyList())
        );
    }
```

---

```java
public class Lazy<T> implements Parser<T> {
    private final Supplier<Parser<T>> supplier;
    private Parser<T> parser;

    public Lazy(Supplier<Parser<T>> supplier) {
        this.supplier = supplier;
    }

    @Override
    public List<Pair<T, String>> parse(String input) {
        if (parser == null) {
            parser = supplier.get();
        }
        return parser.parse(input);
    }

    public static <U> Parser<U> lazy(Supplier<Parser<U>> supplier) {
        return new Lazy<>(supplier);
    }
}
```

---

```java
    public static <T> Parser<List<T>> many(Parser<T> parser) {
        return or(
                map(
                    and(parser, lazy(() -> many(parser))),
                    Many::prepend
                ),
                succeed(Collections.emptyList())
        );
    }
```

---

## Demo

> Parse correctly nested parenthesis
> <pre> 
> 
> ()
> (())
> ()()
> (()())
> (())()
> ...
> </pre>

---

* ### Model
* ### Grammar
* ### Parser

---

![The foyer of momath; the national museum of mathematics](image/momath.jpeg)

---

## [funcj.parser](https://github.com/typemeta/funcj/tree/master/parser)

---

* [https://fifth-postulate.nl/parser-combinators/guide/](https://fifth-postulate.nl/parser-combinators/guide/)
* twitter: [@daan_van_berkel](https://twitter.com/daan_van_berkel)
* GitHub: [dvberkel](https://github.com/dvberkel) 
* Instagram: [daan.vanberkel](https://www.instagram.com/daan.vanberkel/)