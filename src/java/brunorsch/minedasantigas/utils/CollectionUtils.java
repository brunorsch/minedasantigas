package brunorsch.minedasantigas.utils;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CollectionUtils {
    public static <T> Set<T> setOf(T... elements) {
        return new HashSet<>(asList(elements));
    }

    @SafeVarargs
    public static <K, V> Map<K, V> mapOf(Map.Entry<K, V>... entries) {
        HashMap<K, V> map = new HashMap<>();

        for (final Entry<K, V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }

    public static <K, V> Map.Entry<K, V> pair(K key, V value) {
        return new Entry<K, V>() {
            @Override
            public K getKey() {
                return key;
            }

            @Override
            public V getValue() {
                return value;
            }

            @Override
            public V setValue(final V value) {
                throw new UnsupportedOperationException();
            }
        };
    }
}