package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Character.character;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterTest {
    private Parser<java.lang.Character> parser;

    @BeforeEach
    public void createParser() {
        parser = character('a');
    }
    @Test
    public void shouldSucceedOnAnyInputThatStartsWithTarget() {
        String any = "aBCD";

        List<Pair<java.lang.Character, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair('a', "BCD")), results);
    }

    @Test
    public void shouldFailOnAnyInputThatDoesNotStartsWithTarget() {
        String any = "ABCD";

        List<Pair<java.lang.Character, String>> results = parser.parse(any);

        assertTrue(results.isEmpty());
    }
}
