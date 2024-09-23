package workshop2.functional_interfaces;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Producer<String, String> producer = new Producer<>();

        Supplier<String> supplier = producer.customSupplier("Zajavka");
        System.out.println(supplier.get());

        Consumer<String> consumer = producer.customConsumer();
        consumer.accept("Consumer");

        Function<String, String> function = producer.customFunction("abc");
        System.out.println(function.apply("abc"));
    }
}
