package nl.fifthpostulate.parser.utility;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import nl.fifthpostulate.parser.combinator.Many;
import nl.fifthpostulate.parser.combinator.Or;

import java.util.Arrays;
import java.util.List;

import static nl.fifthpostulate.parser.base.Fail.fail;
import static nl.fifthpostulate.parser.combinator.And.and;
import static nl.fifthpostulate.parser.combinator.Filter.filter;
import static nl.fifthpostulate.parser.combinator.Many.many;
import static nl.fifthpostulate.parser.combinator.Map.map;

public class Utility {
    private Utility(){}

    public static <T> Parser<List<T>> many1(Parser<T> parser) {
        return map(and(parser, many(parser)), Many::prepend);
    }

    public static <U, V> Parser<U> keepLeft(Parser<U> left, Parser<V> right) {
        return map(and(left, right), Pair::first);
    }

    public static <U, V> Parser<V> keepRight(Parser<U> left, Parser<V> right) {
        return map(and(left, right), Pair::second);
    }

    public static <U, V, W> Parser<V> pack(Parser<U> leftDelimiter, Parser<V> content, Parser<W> rightDelimiter) {
        return keepRight(leftDelimiter, keepLeft(content, rightDelimiter));
    }

    public static <T> Parser<T> complete(Parser<T> parser) {
        return filter(parser, p -> p.second().isEmpty());
    }

    public static <T> Parser<T> oneOf(Parser<T>... parsers) {
        return Arrays.stream(parsers)
                .reduce(fail(), Or::or);

    }
}
