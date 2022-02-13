package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Character.character;
import static nl.fifthpostulate.parser.combinator.And.and;
import static nl.fifthpostulate.parser.combinator.Just.just;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JustTest {
    private Parser<Character> parser;

    @BeforeEach
    public void createParser() {
        parser = just(character('a'));
    }
    @Test
    public void shouldSucceedOnAnyInputThatStartsWithTargetAndNothingElse() {
        String any = "a";

        List<Pair<Character, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair('a', "")), results);
    }

    @Test
    public void shouldFailOnAnyInputThatDoesNotStartsWithSequenceOfTargets() {
        String any = "X";

        List<Pair<Character, String>> results = parser.parse(any);

        assertTrue(results.isEmpty());
    }

    @Test
    public void shouldFailOnAnyInputThatContainsMoreThenTarget() {
        String any = "aBCD";

        List<Pair<Character, String>> results = parser.parse(any);

        assertTrue(results.isEmpty());
    }
}
