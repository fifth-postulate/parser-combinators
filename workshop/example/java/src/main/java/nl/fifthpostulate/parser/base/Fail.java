package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.Collections;
import java.util.List;

public record Fail<T>() implements Parser<T> {
    @Override
    public List<Pair<T, String>> parse(String input) {
        return Collections.emptyList();
    }

    public static <U> Parser<U> fail() {
        return new Fail<>();
    }
}
