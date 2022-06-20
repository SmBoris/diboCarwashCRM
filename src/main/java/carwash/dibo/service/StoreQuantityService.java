package carwash.dibo.service;

import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.StoreQuantity;

public interface StoreQuantityService {

    StoreQuantity findByName(String name);

    int getCurrentQuantityByName(String name);

    void save(StoreQuantity storeQuantity);
    void saveByName(String name);
    void updateCurrentQuantity(String name, int newValue) throws CantBeNegativeException;
    void addNew(String name, int quantity);
}
