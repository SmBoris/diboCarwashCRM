package carwash.dibo.impl;

import carwash.dibo.common.AutoChemistryStatuses;
import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.AutoChemistry;
import carwash.dibo.repository.AutoChemistryRepository;
import carwash.dibo.service.AutoChemistryService;
import carwash.dibo.service.StoreQuantityService;
import carwash.dibo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AutoChemistryServiceImpl implements AutoChemistryService {
    AutoChemistryRepository autoChemistryRepository;
    StoreQuantityService storeQuantityService;
    UserService userService;

    @Override
    public void save(AutoChemistry autoChemical) {
        autoChemistryRepository.save(autoChemical);
    }

    @Override
    public void refueled(String name, int quantity) throws CantBeNegativeException {
        storeQuantityService.updateCurrentQuantity(name, storeQuantityService.getCurrentQuantityByName(name) - quantity);
        AutoChemistry autoChemical = new AutoChemistry();
        autoChemical.setDate(new Date());
        autoChemical.setName(name);
        autoChemical.setQuantityToChange(quantity);
        autoChemical.setStatus(AutoChemistryStatuses.REFUELED);
        autoChemical.setCurrentQuantity(storeQuantityService.getCurrentQuantityByName(name));
        autoChemical.setUser(userService.getCurrentUser());

        save(autoChemical);
    }

    @Override
    public void addPurchase(String name, int purchaseQuantity) throws CantBeNegativeException {
        storeQuantityService.updateCurrentQuantity(name, storeQuantityService.getCurrentQuantityByName(name) + purchaseQuantity);
        AutoChemistry autoChemical = new AutoChemistry();
        autoChemical.setDate(new Date());
        autoChemical.setName(name);
        autoChemical.setQuantityToChange(purchaseQuantity);
        autoChemical.setStatus(AutoChemistryStatuses.PURCHASE);
        autoChemical.setCurrentQuantity(storeQuantityService.getCurrentQuantityByName(name));
        autoChemical.setUser(userService.getCurrentUser());

        save(autoChemical);
    }

    @Override
    public List<AutoChemistry> getLast4Rows() {
        return autoChemistryRepository.getAutoChemicalByDesc()
                .stream()
                .limit(4)
                .collect(Collectors.toList());
    }
}
