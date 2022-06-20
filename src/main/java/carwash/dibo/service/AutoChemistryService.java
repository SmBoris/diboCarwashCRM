package carwash.dibo.service;

import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.AutoChemistry;

import java.util.List;

public interface AutoChemistryService {

    void save(AutoChemistry autoChemical);

    /**
     * Minus required autoChemistry from DB and saving history
     * @param name Name of product
     * @param quantity Quantity to change
     * @throws CantBeNegativeException Can't be negative
     */
    void refueled(String name, int quantity) throws CantBeNegativeException;

    /**
     * Plus required autoChemistry to DB and saving history
     * @param name Name of Product
     * @param purchaseQuantity Quantity to change
     * @throws CantBeNegativeException Can't be negative
     */
    void addPurchase(String name, int purchaseQuantity) throws CantBeNegativeException;

    /**
     * For UI use method sort and fetch rows
     * @return Last four rows from DB by in DESC
     */
    List<AutoChemistry> getLast4Rows();
}
