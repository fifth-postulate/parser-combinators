layout: true
class: middle, center

---

# Parsing

---
name: how-to

> ## How to extract data from structured text?

---
template: how-to

## `String.split`

---
template: how-to

## regular expressions

---

![A cat laying on a keyboard](image/cat-keyboard.jpeg)

---

![xkcd on regular expressions](https://imgs.xkcd.com/comics/regular_expressions.png)

---
template: how-to

## Parsing

---
template: how-to

## Parsing??

---

## Parsing

> Extracting meaning from stuctured input

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

---

## [guide](https://fifth-postulate.nl/parser-combinators/guide/)

---

## What is next?
