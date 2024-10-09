package workshop3.services;

public class Pair<T, U> {
    private final T firstItem;
    private final U secondItem;

    public Pair(T first, U second) {
        this.firstItem = first;
        this.secondItem = second;
    }

    public T getFirstItem() {
        return firstItem;
    }

    public U getSecondItem() {
        return secondItem;
    }
}
