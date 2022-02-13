package nl.fifthpostulate.parser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Results {
    public static <T> void areInOrder(List<Pair<List<T>, String>> actual, Pair<List<T>, String>... pairs) {
        assertEquals(actual.size(), pairs.length);
        for (int index = 0; index < pairs.length; index++) {
            assertAt(index, pairs[index], actual);
        }
    }

    private static <T> void assertAt(int index, Pair<List<T>, String> expected, List<Pair<List<T>, String>> actual) {
        assertAll(
                () -> assertTrue(index < actual.size()),
                () -> assertIterableEquals(actual.get(index).first(), expected.first()),
                () -> assertEquals(actual.get(index).second(), expected.second())
        );
    }
}
