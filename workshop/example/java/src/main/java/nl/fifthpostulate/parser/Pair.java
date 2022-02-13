package nl.fifthpostulate.parser;

import java.util.function.Function;

public record Pair<L, R>(L first, R second) {
    public <V> Pair<V, R> mapFirst(Function<L, V> transform) {
        return new Pair<>(transform.apply(first), second);
    }

    public static <U, V> Pair<U, V> pair(U first, V second) {
        return new Pair<>(first, second);
    }
}
