package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Token.token;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenTest {
    private Parser<String> parser;

    @BeforeEach
    public void createParser() {
        parser = token("let");
    }

    @Test
    public void shouldSucceedOnAnyInputThatStartsWithToken() {
        String any = "let x = 37";

        List<Pair<String, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair("let", " x = 37")), results);
    }

    @Test
    public void shouldFailOnAnyInputThatDoesNotStartsWithToken() {
        String any = "ABCD";

        List<Pair<String, String>> results = parser.parse(any);

        assertTrue(results.isEmpty());
    }
}
