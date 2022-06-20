package carwash.dibo.impl;

import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.StoreQuantity;
import carwash.dibo.repository.StoreRepository;
import carwash.dibo.service.StoreQuantityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class StoreQuantityServiceImpl implements StoreQuantityService {
    StoreRepository storeRepository;

    @Override
    public StoreQuantity findByName(String name) {
        return storeRepository.findOneByName(name).orElseThrow(() -> new EntityNotFoundException(name));
    }

    @Override
    public int getCurrentQuantityByName(String name) {
        return findByName(name).getCurrentQuantity();
    }

    @Override
    public void save(StoreQuantity storeQuantity) {
         storeRepository.save(storeQuantity);
    }

    @Override
    public void saveByName(String name) {
    }

    @Override
    public void updateCurrentQuantity(String name, int newValue) throws CantBeNegativeException {
        StoreQuantity goods = findByName(name);

        if (isPossibleToChange(newValue)) {
            goods.setCurrentQuantity(newValue);
            save(goods);
            log.info("The " + name + " quantity are updated");
        }
        else throw new CantBeNegativeException("The quantity cannot be negative");
    }

    @Override
    public void addNew(String name, int quantity) {
        Optional<StoreQuantity> goods = storeRepository.findOneByName(name);
        if (!goods.isPresent()){
            log.warn("The goods with name : " + name + " already exist");
            return;
        }
        StoreQuantity storeQuantity = new StoreQuantity();
        storeQuantity.setName(name);
        storeQuantity.setCurrentQuantity(quantity);
        save(storeQuantity);
        log.info("The new auto chemistry storeQuantity with name: " + name + " added to db");
    }

    public boolean isPossibleToChange(int newValue){
        return newValue >= 0;
    }
}
