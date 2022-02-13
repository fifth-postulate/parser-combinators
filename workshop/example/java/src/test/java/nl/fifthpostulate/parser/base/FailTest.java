package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.fifthpostulate.parser.base.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FailTest {
    @Test
    public void shouldFailOnAnyInput() {
        Parser<Long> parser = fail();
        String any = "could literally be any String";

        List<Pair<Long, String>> results = parser.parse(any);

        assertTrue(results.isEmpty());
    }
}
