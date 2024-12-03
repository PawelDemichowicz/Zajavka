package workshop2.functional_interfaces;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Producer<T, R> {

    public Supplier<T> customSupplier(T t) {
        return () -> t;
    }

    public Consumer<T> customConsumer() {
        return t -> {
            System.out.println(t);
        };
    }

    public Function<T, T> customFunction(T t) {
        return u -> u.toString().compareTo(t.toString()) > 0 ? u : t;
    }
}
