package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Character.character;
import static nl.fifthpostulate.parser.base.Predicate.predicate;
import static nl.fifthpostulate.parser.combinator.And.and;
import static nl.fifthpostulate.parser.combinator.Map.map;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapTest {
    private Parser<Long> parser;

    @BeforeEach
    public void createParser() {
        parser = map(predicate(Character::isAlphabetic), c -> Long.valueOf(c - 'a'));
    }
    @Test
    public void shouldSucceedOnAnyInputThatStartsWithTargetAndMapResult() {
        String any = "aBCD";

        List<Pair<Long, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair(0L, "BCD")), results);
    }

    @Test
    public void shouldFailOnAnyInputThatDoesNotStartsWithTarget() {
        String any = "_BCD";

        List<Pair<Long, String>> results = parser.parse(any);

        assertTrue(results.isEmpty());
    }
}
