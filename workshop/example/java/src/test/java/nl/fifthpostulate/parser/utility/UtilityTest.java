package nl.fifthpostulate.parser.utility;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import nl.fifthpostulate.parser.Results;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Character.character;
import static nl.fifthpostulate.parser.combinator.Many.many;
import static nl.fifthpostulate.parser.utility.Utility.*;
import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {
    @Test
    public void many1SucceedWhenThereIsAtLeastASingleTargetStartingTheInput() {
        Parser<List<Character>> parser = many1(character('a'));
        String input = "aaaBCD";

        List<Pair<List<Character>, String>> results = parser.parse(input);

        Results.areInOrder(results,
                pair(Arrays.asList('a', 'a', 'a'), "BCD"),
                pair(Arrays.asList('a', 'a'), "aBCD"),
                pair(Arrays.asList('a'), "aaBCD")
        );
    }

    @Test
    public void many1ShouldFailWhenInputDoesNotStartWithTarget() {
        Parser<List<Character>> parser = many1(character('a'));
        String input = "BCD";

        List<Pair<List<Character>, String>> results = parser.parse(input);

        assertTrue(results.isEmpty());
    }

    @Test
    public void keepLeftShouldRetainTheFirstParserResultWhenSucceeding() {
        Parser<Character> parser = keepLeft(character('a'), character('b'));
        String input = "abCD";

        List<Pair<Character, String>> results = parser.parse(input);

        assertIterableEquals(Collections.singleton(pair('a', "CD")), results);
    }

    @Test
    public void keepRightShouldRetainTheSecondParserResultWhenSucceeding() {
        Parser<Character> parser = keepRight(character('a'), character('b'));
        String input = "abCD";

        List<Pair<Character, String>> results = parser.parse(input);

        assertIterableEquals(Collections.singleton(pair('b', "CD")), results);
    }

    @Test
    public void packShouldReturnSucceedWhenContentIsPackedInDelimiters() {
        Parser<Character> parser = pack(character('('), character('a'), character(')'));
        String input = "(a)BC";

        List<Pair<Character, String>> results = parser.parse(input);

        assertIterableEquals(Collections.singleton(pair('a', "BC")), results);
    }

    @Test
    public void completeShouldOnlySucceedWhenTheEntireInputIsConsumed() {
        Parser<List<Character>> parser = complete(many(character('a')));
        String input = "aaaaa";

        List<Pair<List<Character>, String>> results = parser.parse(input);

        Results.areInOrder(results,
            pair(Arrays.asList('a', 'a', 'a', 'a', 'a'), "")
        );
    }

    @Test
    public void oneOfShouldGeneralizeOr() {
        Parser<Character> parser = oneOf(
                character('a'),
                character('A'),
                character('b'),
                character('B')
        );

        assertFalse(parser.parse("aCD").isEmpty());
        assertFalse(parser.parse("ACD").isEmpty());
        assertFalse(parser.parse("bCD").isEmpty());
        assertFalse(parser.parse("BCD").isEmpty());
        assertTrue(parser.parse("CD").isEmpty());
    }
}
