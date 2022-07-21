package carwash.dibo.impl;

import carwash.dibo.warehouse.AutoChemistryGood;
import carwash.dibo.warehouse.AutoChemistryStatus;
import carwash.dibo.warehouse.WarehouseUsable;
import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.AutoChemistry;
import carwash.dibo.repository.AutoChemistryRepository;
import carwash.dibo.service.AutoChemistryService;
import carwash.dibo.warehouse.WarehouseService;
import carwash.dibo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AutoChemistryServiceImpl implements AutoChemistryService, WarehouseUsable<AutoChemistryStatus> {

    AutoChemistryRepository autoChemistryRepository;
    WarehouseService warehouseService;
    UserService userService;

    @Override
    public void save(AutoChemistry autoChemistry) {
        autoChemistryRepository.save(autoChemistry);
    }

    @Override
    public String refueled(AutoChemistry autoChemistry) {
        String message;
        boolean isPressureWashFoam = isPressureWashFoam(autoChemistry.getName());

        //Use active foam when refueled pressure active foam
        if (isPressureWashFoam){
            if (isPurchase(autoChemistry.getStatus())){
                return  "Предварительная мойка не закупается на склад";
            }
            autoChemistry.setName(AutoChemistryGood.ACTIVE_FOAM.getName());
        }

        try {
            message = warehouseService.updateCurrentQuantity(
                    autoChemistry.getName(),
                    autoChemistry.getQuantityToChange(),
                    isPurchase(autoChemistry.getStatus()), AutoChemistryServiceImpl.class);
        }
        catch (CantBeNegativeException ex){
            return "На складе не хватает необходимого количества";
        }

        autoChemistry.setDate(new Date());
        autoChemistry.setCurrentQuantity(warehouseService.getCurrentQuantityByName(autoChemistry.getName()));
        autoChemistry.setUser(userService.getCurrentUser());
        
        //Use pressure wash foam for history refueled
        if (isPressureWashFoam) { autoChemistry.setName(AutoChemistryGood.PRESSURE_WASH_FOAM.getName());}

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

    @Override
    public boolean isPurchase(AutoChemistryStatus status) {
        return status.equals(AutoChemistryStatus.PURCHASE);
    }

    private boolean isPressureWashFoam(String name){
        return name.equals(AutoChemistryGood.PRESSURE_WASH_FOAM.getName());
    }
}
