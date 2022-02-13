package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record Filter<T>(Parser<T> parser, Predicate<Pair<T, String>> predicate) implements Parser<T> {
    @Override
    public List<Pair<T, String>> parse(String input) {
        return parser.parse(input).stream().filter(predicate).collect(Collectors.toList());
    }

    public static <U> Parser<U> filter(Parser<U> parser, Predicate<Pair<U, String>> predicate) {
        return new Filter<>(parser, predicate);
    }
}
