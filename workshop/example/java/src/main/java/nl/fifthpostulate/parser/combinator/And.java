package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static nl.fifthpostulate.parser.Pair.pair;

public record And<L, R>(Parser<L> first, Parser<R> second) implements Parser<Pair<L, R>> {
    @Override
    public List<Pair<Pair<L, R>, String>> parse(String input) {
        return first.parse(input)
                .stream()
                .flatMap(this::proceedWithSecond)
                .collect(Collectors.toList());
    }

    private Stream<Pair<Pair<L, R>, String>> proceedWithSecond(Pair<L, String> intermediate) {
        return second.parse(intermediate.second())
                .stream()
                .map(q -> q.mapFirst(r -> pair(intermediate.first(), r)));
    }

    public static <U, V> Parser<Pair<U, V>> and(Parser<U> first, Parser<V> second) {
        return new And<>(first, second);
    }

}
