package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
