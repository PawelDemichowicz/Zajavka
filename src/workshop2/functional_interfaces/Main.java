package workshop2.functional_interfaces;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        generateProducer();
        generateTransformer();
    }

    private static void generateProducer() {
        Producer<String, String> producer = new Producer<>();

        Supplier<String> supplier = producer.customSupplier("Zajavka");
        System.out.println(supplier.get());

        Consumer<String> consumer = producer.customConsumer();
        consumer.accept("Consumer");

        Function<String, String> function = producer.customFunction("abc");
        System.out.println(function.apply("abc"));
    }

    private static void generateTransformer() {
        Function<String, Integer> function = Transformer.customFunction();
        Optional<Integer> optional = Optional.of("Zajavka").map(function);
        System.out.println(optional);

        UnaryOperator<BigDecimal> unaryOperator = Transformer.customUnaryOperator();
        Optional<BigDecimal> optional2 = Optional.of(BigDecimal.valueOf(3.14)).map(unaryOperator);
        System.out.println(optional2);
    }
}
