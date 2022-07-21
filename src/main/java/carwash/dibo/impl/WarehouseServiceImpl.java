package carwash.dibo.impl;

import carwash.dibo.warehouse.WarehouseUsable;
import carwash.dibo.exception.CantBeNegativeException;
import carwash.dibo.model.Warehouse;
import carwash.dibo.repository.WarehouseRepository;
import carwash.dibo.warehouse.WarehouseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {

    WarehouseRepository warehouseRepository;

    @Override
    public Warehouse findByName(String name) {
        return warehouseRepository.findOneByName(name).orElseThrow(() -> new EntityNotFoundException(name));
    }

    @Override
    public int getCurrentQuantityByName(String name) {
        return findByName(name).getCurrentQuantity();
    }

    @Override
    public String updateCurrentQuantity(String name, int value, boolean isPurchase, Class<? extends WarehouseUsable<?>> clazz)
            throws CantBeNegativeException {

        Warehouse goods = findByName(name);
        int newValue;

        if (isPurchase) {
            newValue = goods.getCurrentQuantity() + value;
            save(goods, newValue);
            return name + " успешно добавлено";
        }

        else newValue = goods.getCurrentQuantity() - value;

        if (isPossibleToChange(newValue)) {
            save(goods, newValue);
            return name + " успешно списано";
        }

        else throw new CantBeNegativeException("The quantity for update can't be negative");
    }

    @Override
    public void save(Warehouse warehouseQuantity) {
         warehouseRepository.save(warehouseQuantity);
    }

    @Override
    public void saveByName(String name) {

    }

    private void save(Warehouse good, int newValue){
        good.setCurrentQuantity(newValue);
        save(good);
        log.info("The " + good.getName() + " quantity are updated");
    }

    @Override
    public void addNew(String name, int quantity) {
        Optional<Warehouse> goods = warehouseRepository.findOneByName(name);
        if (!goods.isPresent()){
            log.warn("The goods with name : " + name + " already exist");
            return;
        }
        Warehouse warehouseQuantity = new Warehouse();
        warehouseQuantity.setName(name);
        warehouseQuantity.setCurrentQuantity(quantity);
        save(warehouseQuantity);
        log.info("The new auto chemistry warehouseQuantity with name: " + name + " added to db");
    }

    private boolean isPossibleToChange(int newValue){
        return newValue >= 0;
    }
}
