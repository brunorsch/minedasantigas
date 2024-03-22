package brunorsch.minedasantigas.utils;

import java.util.Optional;
import java.util.function.Consumer;

public class OptionalUtils {
    public static <T> void ifPresentOrElse(Optional<T> optional, Consumer<T> ifPresent, Runnable orElse) {
        if(optional.isPresent()) {
            ifPresent.accept(optional.get());
        } else {
            orElse.run();
        }
    }
}