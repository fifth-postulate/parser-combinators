package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.List;
import java.util.function.Supplier;

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
