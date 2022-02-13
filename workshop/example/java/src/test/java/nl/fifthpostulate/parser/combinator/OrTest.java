package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Character.character;
import static nl.fifthpostulate.parser.combinator.Or.or;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrTest {
    private Parser<java.lang.Character> parser;

    @BeforeEach
    public void createParser() {
        parser = or(character('a'), character('A'));
    }
    @Test
    public void shouldSucceedOnAnyInputThatStartsWithLeftTarget() {
        String any = "aBCD";

        List<Pair<java.lang.Character, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair('a', "BCD")), results);
    }

    @Test
    public void shouldSucceedOnAnyInputThatStartsWithRightTarget() {
        String any = "ABCD";

        List<Pair<java.lang.Character, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair('A', "BCD")), results);
    }

    @Test
    public void shouldFailOnAnyInputThatDoesNotStartsWithEitherTarget() {
        String any = "XBCD";

        List<Pair<java.lang.Character, String>> results = parser.parse(any);

        assertTrue(results.isEmpty());
    }
}
