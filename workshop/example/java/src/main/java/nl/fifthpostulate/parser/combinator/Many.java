package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.base.Succeed.succeed;
import static nl.fifthpostulate.parser.combinator.And.and;
import static nl.fifthpostulate.parser.combinator.Map.map;
import static nl.fifthpostulate.parser.combinator.Or.or;

public class Many<T> implements Parser<List<T>> {
    private final Parser<List<T>> parser;

    public Many(Parser<T> parser) {
        this.parser = or(
                map(and(parser, Lazy.lazy(() -> many(parser))), Many::prepend),
                succeed(Collections.emptyList())
        );
    }

    public static <U> List<U> prepend(Pair<U, List<U>> p) {
        List<U> result = new ArrayList<>();
        result.add(p.first());
        result.addAll(p.second());
        return result;
    }

    public List<Pair<List<T>, String>> parse(String input) {
        return parser.parse(input);
    }

    public static <U> Parser<List<U>> many(Parser<U> parser) {
        return new Many<>(parser);
    }
}
