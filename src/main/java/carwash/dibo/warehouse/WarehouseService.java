package carwash.dibo.warehouse;

import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.Warehouse;

public interface WarehouseService {

    Warehouse findByName(String name);

    int getCurrentQuantityByName(String name);

    String updateCurrentQuantity(String name, int newValue, boolean isPurchase, Class<? extends WarehouseUsable<?>> clazz)
            throws CantBeNegativeException;

    void save(Warehouse warehouseQuantity);
    void saveByName(String name);
    void addNew(String name, int quantity);
}
