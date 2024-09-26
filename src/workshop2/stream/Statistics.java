package workshop2.stream;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Statistics {
    private static final List<Purchase> purchases = DataFactory.produce();

    public static long amountsOfClients() {
        return purchases.stream()
                .map(Purchase::getBuyer)
                .distinct()
                .count();
    }

    public static long amountsOfClientWithPaymentMethod(Purchase.Payment payment) {
        return purchases.stream()
                .filter(purchase -> payment.equals(purchase.getPayment()))
                .map(Purchase::getBuyer)
                .distinct()
                .count();
    }

    public static long amountsOfPurchasesInCurrency(Money.Currency currency) {
        return purchases.stream()
                .filter(purchase -> currency.equals(purchase.getProduct().getPrice().getCurrency()))
                .count();
    }

    public static long amountsOfUniqueProductsInCurrency(Money.Currency currency) {
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
}
