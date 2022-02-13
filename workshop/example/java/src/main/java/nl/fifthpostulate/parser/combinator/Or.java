package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public record Or<T>(Parser<T> left, Parser<T> right) implements Parser<T> {
    @Override
    public List<Pair<T, String>> parse(String input) {
        List<Pair<T, String>> result = new ArrayList<>(left.parse(input));
        result.addAll(right.parse(input));
        return result;
    }

    public static <U> Parser<U> or(Parser<U> left, Parser<U> right) {
        return new Or<>(left, right);
    }
}
