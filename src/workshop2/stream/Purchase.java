package workshop2.stream;

import java.time.LocalDate;

public class Purchase {
    private final Client buyer;
    private final Product product;
    private final long quantity;
    private final Delivery delivery;
    private final Payment payment;
    private final LocalDate when;
    private final Status status;

    public Purchase(Client buyer, Product product, long quantity, Delivery delivery, Payment payment, LocalDate when) {
        this.buyer = buyer;
        this.product = product;
        this.quantity = quantity;
        this.delivery = delivery;
        this.payment = payment;
        this.when = when;
        this.status = Status.PAID;
    }

    public Purchase(Purchase purchase, Status status) {
        this.buyer = purchase.buyer;
        this.product = purchase.product;
        this.quantity = purchase.quantity;
        this.delivery = purchase.delivery;
        this.payment = purchase.payment;
        this.when = purchase.when;
        this.status = status;
    }

    public Client getBuyer() {
        return buyer;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public Payment getPayment() {
        return payment;
    }

    public Status getStatus() {
        return status;
    }

    public enum Delivery {
        IN_POST,
        UPS,
        DHL
    }

    public enum Payment {
        CASH,
        BLIK,
        CREDIT_CARD
    }

    public enum Status {
        PAID,
        SENT,
        DONE
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "buyer=" + buyer +
                ", product=" + product +
                ", quantity=" + quantity +
                ", delivery=" + delivery +
                ", payment=" + payment +
                ", when=" + when +
                ", status=" + status +
                '}';
    }
}