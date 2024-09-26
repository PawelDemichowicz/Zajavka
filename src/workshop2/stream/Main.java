package workshop2.stream;

public class Main {
    public static void main(String[] args) {
        System.out.println(Statistics.amountsOfClients());
        System.out.println(Statistics.amountsOfClientWithPaymentMethod(Purchase.Payment.BLIK));
        System.out.println(Statistics.amountsOfClientWithPaymentMethod(Purchase.Payment.CREDIT_CARD));
        System.out.println(Statistics.amountsOfPurchasesInCurrency(Money.Currency.EUR));
        System.out.println(Statistics.amountsOfUniqueProductsInCurrency(Money.Currency.EUR));
        PrintingUtils.printMap(Statistics.calculateSpendMoneyByClientWithCurrency(Money.Currency.PLN));
    }
}
