package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;

public record Token(String token) implements Parser<String> {
    @Override
    public List<Pair<String, String>> parse(String input) {
        if (input.startsWith(token)) {
            return Collections.singletonList(pair(token, input.substring(token.length())));
        } else {
            return Collections.emptyList();
        }
    }

    public static Parser<String> token(String token) {
        return new Token(token);
    }
}
