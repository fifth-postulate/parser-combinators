package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;

public record Just<T>(Parser<T> parser) implements Parser<T> {
    @Override
    public List<Pair<T, String>> parse(String input) {
        return parser.parse(input)
                .stream()
                .filter(p -> p.second().isEmpty())
                .collect(Collectors.toList());
    }

    public static <U> Parser<U> just(Parser<U> parser){
        return new Just<>(parser);
    }
}
