package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;

public record Predicate(
        java.util.function.Predicate<java.lang.Character> predicate) implements Parser<java.lang.Character> {
    @Override
    public List<Pair<java.lang.Character, String>> parse(String input) {
        if (input.length() > 0) {
            char[] characters = new char[1];
            input.getChars(0, 1, characters, 0);
            char character = characters[0];
            if (predicate.test(character)) {
                return Collections.singletonList(pair(characters[0], input.substring(1)));
            }
        }
        return Collections.emptyList();
    }

    public static Parser<java.lang.Character> predicate(java.util.function.Predicate<java.lang.Character> predicate) {
        return new Predicate(predicate);
    }
}
