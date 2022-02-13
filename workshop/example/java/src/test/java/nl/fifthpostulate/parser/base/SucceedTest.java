package nl.fifthpostulate.parser.base;

import nl.fifthpostulate.parser.Pair;
import nl.fifthpostulate.parser.Parser;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static nl.fifthpostulate.parser.Pair.pair;
import static nl.fifthpostulate.parser.base.Succeed.succeed;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class SucceedTest {
    @Test
    public void shouldSucceedOnAnyInput() {
        Parser<Long> parser = succeed(37L);
        String any = "could literally be any String";

        List<Pair<Long, String>> results = parser.parse(any);

        assertIterableEquals(Collections.singleton(pair(37L, any)), results);
    }
}
