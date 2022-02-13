package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;

public record Succeed<A>(A result) implements Parser<A> {
    @Override
    public List<Pair<A, String>> parse(String input) {
        return Collections.singletonList(pair(result, input));
    }

    public static <U> Parser<U> succeed(U result) {
        return new Succeed<>(result);
    }
}
