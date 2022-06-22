package carwash.dibo.impl;

import carwash.dibo.common.AutoChemistryStatus;
import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.AutoChemistry;
import carwash.dibo.repository.AutoChemistryRepository;
import carwash.dibo.service.AutoChemistryService;
import carwash.dibo.service.StoreQuantityService;
import carwash.dibo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.IMessage;
import org.springframework.stereotype.Service;

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
    public String refueled(String name, int quantity, AutoChemistryStatus status) {
        
        String message = "";
        
        try {
            message = storeQuantityService.updateCurrentQuantity(name, quantity, status);
        }
        catch (CantBeNegativeException ex){
            return "На складе не хватает необходимого количества";
        }

        AutoChemistry autoChemistry = new AutoChemistry();
        autoChemistry.setDate(new Date());
        autoChemistry.setName(name);
        autoChemistry.setQuantityToChange(quantity);
        autoChemistry.setStatus(status);
        autoChemistry.setCurrentQuantity(storeQuantityService.getCurrentQuantityByName(name));
        autoChemistry.setUser(userService.getCurrentUser());

        save(autoChemistry);

        return message;
    }
    
    @Override
    public List<AutoChemistry> getLast4Rows() {
        return autoChemistryRepository.getAutoChemicalByDesc()
                .stream()
                .limit(4)
                .collect(Collectors.toList());
    }
}
