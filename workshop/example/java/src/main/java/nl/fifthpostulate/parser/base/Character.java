package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Predicate.predicate;

public record Character(java.lang.Character target) implements Parser<java.lang.Character> {
    @Override
    public List<Pair<java.lang.Character, String>> parse(String input) {
        if (input.startsWith(String.valueOf(target))) {
            return Collections.singletonList(pair(target, input.substring(1)));
        } else {
            return Collections.emptyList();
        }
    }

    public static Parser<java.lang.Character> character(char target) {
        return predicate(c -> c == target);
    }
}
