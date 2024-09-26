package workshop2.stream;

public class Main {
    public static void main(String[] args) {
        System.out.println(Statistics.amountOfClients());
        System.out.println(Statistics.amountOfClientWithPaymentMethod(Purchase.Payment.BLIK));
        System.out.println(Statistics.amountOfClientWithPaymentMethod(Purchase.Payment.CREDIT_CARD));
        System.out.println(Statistics.amountOfPurchasesInCurrency(Money.Currency.EUR));
        System.out.println(Statistics.amountOfUniqueProductsInCurrency(Money.Currency.EUR));
        PrintingUtils.printMap(Statistics.calculateSpendMoneyByClientWithCurrency(Money.Currency.PLN));
        PrintingUtils.printMap(Statistics.calculateAmountProductsWithCategoryPerClient(Product.Category.HOBBY));
        System.out.println(Statistics.calculatePurchasesWithOrderStatus(Purchase.Status.DONE));
        System.out.println(Statistics.calculateClientsWhoBoughtProductWithCurrency(Money.Currency.EUR));
        PrintingUtils.printMap(Statistics.shoppingListForClientWithCurrency(Money.Currency.EUR));
        PrintingUtils.printMap(Statistics.soldProductsPerClientBirthYearUsingGroupingBy());
        PrintingUtils.printMap(Statistics.soldProductsPerClientBirthYearUsingToMap());
        System.out.println(Statistics.secondPopularProductSold());
    }
}
