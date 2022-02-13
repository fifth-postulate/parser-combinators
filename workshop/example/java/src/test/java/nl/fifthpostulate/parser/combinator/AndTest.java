package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.combinator.And.and;
import static nl.fifthpostulate.parser.base.Character.character;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AndTest {
    private Parser<Pair<java.lang.Character, java.lang.Character>> parser;

    @BeforeEach
    public void createParser() {
        parser = and(character('a'), character('b'));
    }
    @Test
    public void shouldSucceedOnAnyInputThatStartsWithFirstFollowedByTheSecond() {
        String any = "abCD";

        List<Pair<Pair<java.lang.Character, java.lang.Character>, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair(pair('a', 'b'), "CD")), results);
    }

    @Test
    public void shouldFailOnAnyInputThatDoesNotStartsWithSequenceOfTargets() {
        String any = "XBCD";

        List<Pair<Pair<java.lang.Character, java.lang.Character>, String>> results = parser.parse(any);

        assertTrue(results.isEmpty());
    }
}
