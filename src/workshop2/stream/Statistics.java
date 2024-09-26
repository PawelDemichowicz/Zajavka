package workshop2.stream;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Statistics {
    private static final List<Purchase> purchases = DataFactory.produce();

    public static long amountOfClients() {
        return purchases.stream()
                .map(Purchase::getBuyer)
                .distinct()
                .count();
    }

    public static long amountOfClientWithPaymentMethod(Purchase.Payment payment) {
        return purchases.stream()
                .filter(purchase -> payment.equals(purchase.getPayment()))
                .map(Purchase::getBuyer)
                .distinct()
                .count();
    }

    public static long amountOfPurchasesInCurrency(Money.Currency currency) {
        return purchases.stream()
                .filter(purchase -> currency.equals(purchase.getProduct().getPrice().getCurrency()))
                .count();
    }

    public static long amountOfUniqueProductsInCurrency(Money.Currency currency) {
        return purchases.stream()
                .map(Purchase::getProduct)
                .distinct()
                .filter(product -> currency.equals(product.getPrice().getCurrency()))
                .count();
    }

    public static TreeMap<String, BigDecimal> calculateSpendMoneyByClientWithCurrency(Money.Currency currency) {
        return purchases.stream()
                .filter(p -> currency.equals(p.getProduct().getPrice().getCurrency()))
                .collect(Collectors.toMap(
                                k -> k.getBuyer().getId(),
                                v -> v.getProduct().getPrice().getValue().multiply(BigDecimal.valueOf(v.getQuantity())),
                                BigDecimal::add,
                                TreeMap::new
                        )
                );
    }

    public static TreeMap<String, Long> calculateAmountProductsWithCategoryPerClient(Product.Category category) {
        return purchases.stream()
                .filter(p -> category.equals(p.getProduct().getCategory()))
                .filter(p -> p.getQuantity() > 1)
                .collect(Collectors.toMap(
                                k -> k.getBuyer().getId(),
                                Purchase::getQuantity,
                                Long::sum,
                                TreeMap::new
                        )
                );
    }

    public static long calculatePurchasesWithOrderStatus(Purchase.Status status) {
        return purchases.stream()
                .map(purchase -> new Purchase(purchase, OrderService.checkOrderStatus(purchase)))
                .filter(purchase -> status.equals(purchase.getStatus()))
                .count();
    }

    public static long calculateClientsWhoBoughtProductWithCurrency(Money.Currency currency) {
        return purchases.stream()
                .filter(p -> currency.equals(p.getProduct().getPrice().getCurrency()))
                .map(Purchase::getBuyer)
                .distinct()
                .count();
    }

    public static TreeMap<String, List<Purchase>> shoppingListForClientWithCurrency(Money.Currency currency) {
        return purchases.stream()
                .filter(p -> currency.equals(p.getProduct().getPrice().getCurrency()))
                .collect(Collectors.groupingBy(
                        p -> p.getBuyer().getId(),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    public static TreeMap<String, Set<Product.Category>> soldProductsPerClientBirthYearUsingGroupingBy() {
        return purchases.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getBuyer().getPesel().toString().substring(0, 2),
                        TreeMap::new,
                        Collectors.mapping(
                                p -> p.getProduct().getCategory(),
                                Collectors.toSet()
                        )
                ));
    }

    public static TreeMap<String, Set<Product.Category>> soldProductsPerClientBirthYearUsingToMap() {
        return purchases.stream()
                .collect(Collectors.toMap(
                        p -> p.getBuyer().getPesel().toString().substring(0, 2),
                        p -> Set.of(p.getProduct().getCategory()),
                        (c1, c2) -> Stream.concat(c1.stream(), c2.stream()).collect(Collectors.toSet()),
                        TreeMap::new
                ));
    }

    public static Pair<String, Long> secondPopularProductSold() {
        Map<String, Long> purchaseList = purchases.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getProduct().getId(),
                        Collectors.mapping(
                                Purchase::getQuantity,
                                Collectors.reducing(0L, Long::sum)
                        )
                ));
        Comparator<? super Pair<String, Long>> customComparator = Comparator.comparing((Pair<String, Long> p) -> p.getV())
                .reversed()
                .thenComparing((Pair<String, Long> p) -> p.getU());

        return purchaseList.entrySet().stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .sorted(customComparator)
                .skip(1)
                .findFirst().orElseThrow(() -> new RuntimeException("Empty purchases list."));
    }


}
