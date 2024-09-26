package workshop2.stream;

import java.util.Map;

public class PrintingUtils {
    public static void printMap(Map<?, ?> map) {
        map.forEach((k, v) -> System.out.format("Key: %s | Value: %s%n", k, v));
    }
}
