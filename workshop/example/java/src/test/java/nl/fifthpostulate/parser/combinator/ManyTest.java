package nl.fifthpostulate.parser.combinator;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import nl.fifthpostulate.parser.Results;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Character.character;
import static nl.fifthpostulate.parser.combinator.Many.many;
import static org.junit.jupiter.api.Assertions.*;

public class ManyTest {
    private Parser<List<Character>> parser;

    @BeforeEach
    public void createParser() {
        parser = many(character('a'));
    }

    @Test
    public void shouldSucceedOnAnyInputDoesNotThatStartsTarget() {
        String any = "BCD";

        List<Pair<List<Character>, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair(Collections.emptyList(), "BCD")), results);
    }

    @Test
    public void shouldSucceedOnAnyInputDoesStartWithSingleTarget() {
        String any = "aBCD";

        List<Pair<List<Character>, String>> results = parser.parse(any);

        Results.areInOrder(results,
                pair(Arrays.asList('a'), "BCD"),
                pair(Collections.emptyList(), "aBCD")
        );
    }

    @Test
    public void shouldSucceedOnAnyInputStartWithMultipleTargets() {
        String any = "aaaaBCD";

        List<Pair<List<Character>, String>> results = parser.parse(any);

        Results.areInOrder(results,
                pair(Arrays.asList('a', 'a', 'a', 'a'), "BCD"),
                pair(Arrays.asList('a', 'a', 'a'), "aBCD"),
                pair(Arrays.asList('a', 'a'), "aaBCD"),
                pair(Arrays.asList('a'), "aaaBCD"),
                pair(Collections.emptyList(), "aaaaBCD")
        );
    }}

