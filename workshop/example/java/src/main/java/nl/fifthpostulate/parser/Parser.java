package nl.fifthpostulate.parser;

import java.util.List;

@FunctionalInterface
public interface Parser<T> {
    List<Pair<T, String>> parse(String input);
}
