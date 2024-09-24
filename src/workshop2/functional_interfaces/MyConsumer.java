package workshop2.functional_interfaces;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MyConsumer {
    public void consumeSupplier(Supplier<String> supplier) {
        System.out.println(supplier.get());
    }

    public void consumeConsumer(Consumer<String> consumer) {
        consumer.accept("Consumer");
    }

    public void consumeFunction(Function<String, Integer> function) {
        System.out.println(function.apply("coś tam"));
    }
}
