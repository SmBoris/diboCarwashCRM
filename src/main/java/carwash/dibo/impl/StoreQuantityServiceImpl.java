package carwash.dibo.impl;

import carwash.dibo.common.AutoChemistryStatus;
import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.StoreQuantity;
import carwash.dibo.repository.StoreRepository;
import carwash.dibo.service.StoreQuantityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
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

    private void save(StoreQuantity good, int newValue){
        good.setCurrentQuantity(newValue);
        save(good);
        log.info("The " + good.getName() + " quantity are updated");
    }

    @Override
    public void saveByName(String name) {
    }

    @Caching(evict = {
            @CacheEvict(value = "foamAvailable", allEntries = true),
            @CacheEvict(value="waxAvailable", allEntries = true),
            @CacheEvict(value = "chemistryList", allEntries = true)})
    @Override
    public String updateCurrentQuantity(String name, int quantityToChange, AutoChemistryStatus status) throws CantBeNegativeException {
        StoreQuantity goods = findByName(name);
        int newValue;

        if (isPurchase(status)) {
            newValue = goods.getCurrentQuantity() + quantityToChange;
            save(goods, newValue);
            return name + " успешно добавлено";
        }
        else newValue = goods.getCurrentQuantity() - quantityToChange;

        if (isPossibleToChange(newValue)) {
            save(goods, newValue);
            return name + " успешно списано";
        }

        else throw new CantBeNegativeException("The quantity for update can't be negative");
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

    private boolean isPossibleToChange(int newValue){
        return newValue >= 0;
    }

    private boolean isPurchase(AutoChemistryStatus status){ return status.equals(AutoChemistryStatus.PURCHASE);}
}
