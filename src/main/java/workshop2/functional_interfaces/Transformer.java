package workshop2.functional_interfaces;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Transformer {
    public static <T, R extends Number> Function<T, R> customFunction() {
        return (T t) -> (R) Integer.valueOf(t.toString().length());
    }

    public static <T extends Number> UnaryOperator<T> customUnaryOperator() {
        return t -> (T) Integer.valueOf(t.intValue() * 2);
    }
}
