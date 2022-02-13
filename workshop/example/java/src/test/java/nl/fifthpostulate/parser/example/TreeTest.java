package nl.fifthpostulate.parser.example;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.fifthpostulate.parser.example.Tree.leaf;
import static nl.fifthpostulate.parser.example.Tree.node;
import static nl.fifthpostulate.parser.utility.Utility.complete;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeTest {
    private Parser<Tree> parser;

    @BeforeEach
    public void createTreeParser() {
        parser = complete(Tree.parser());
    }

    @ParameterizedTest
    @MethodSource("data")
    public void shouldCorrectlyParseInputIntoATree(String input, Tree expected) {
        List<Pair<Tree, String>> results = parser.parse(input);

        assertEquals(1, results.size());
        assertEquals(expected, results.get(0).first());
    }

    public static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("", leaf()),
                Arguments.of("()", node(leaf(),leaf())),
                Arguments.of("()()", node(leaf(), node(leaf(), leaf()))),
                Arguments.of("(())", node(node(leaf(), leaf()), leaf())),
                Arguments.of("(())()", node(node(leaf(), leaf()), node(leaf(), leaf())))
        );
    }
}
