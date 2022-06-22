package carwash.dibo.service;

import carwash.dibo.common.AutoChemistryStatus;
import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.AutoChemistry;

import java.util.List;

public interface AutoChemistryService {

    void save(AutoChemistry autoChemical);

    String refueled(String name, int quantity, AutoChemistryStatus status);

    List<AutoChemistry> getLast4Rows();
}
