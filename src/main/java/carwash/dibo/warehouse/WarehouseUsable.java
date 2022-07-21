package carwash.dibo.warehouse;

public interface WarehouseUsable<E> {
    boolean isPurchase(E status);
}
