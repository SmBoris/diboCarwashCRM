package carwash.dibo.service;

import carwash.dibo.model.AutoChemistry;

import java.util.List;

public interface AutoChemistryService {

    void save(AutoChemistry autoChemical);

    String refueled(AutoChemistry autoChemistry);

    List<AutoChemistry> getLast4Rows();
}
