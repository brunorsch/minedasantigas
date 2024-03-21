package brunorsch.minedasantigas.utils;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.Set;

public class CollectionUtils {
    public static <T> Set<T> setOf(T... elements) {
        return new HashSet<>(asList(elements));
    }
}