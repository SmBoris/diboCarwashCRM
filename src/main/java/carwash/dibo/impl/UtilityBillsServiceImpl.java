package carwash.dibo.impl;

import carwash.dibo.bills.UtilityBillsHandler;
import carwash.dibo.bills.UtilityMeterType;
import carwash.dibo.model.UtilityBills;
import carwash.dibo.repository.UtilityBillsRepository;
import carwash.dibo.service.UtilityBillsService;
import carwash.dibo.utils.DateConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilityBillsServiceImpl implements UtilityBillsService {

    private final UtilityBillsRepository utilityBillsRepository;
    private final UtilityBillsHandler utilityBillsHandler;

    @Override
    public List<UtilityBills> getLast7Rows() {
        return utilityBillsRepository.findTop7ByOrderByIdDesc();
    }

    @Override
    public void save(UtilityMeterType type, int month, int value, int cost) {

        Optional<UtilityBills> utilityBills = utilityBillsRepository.getByMonthAndYear(month);

        int year = DateConverter.getNowIntYear();

        //When we update December value at January month in next year
        if (DateConverter.previousMonthInNextYear(month)){
            year--;
        }

        if (!utilityBills.isPresent()){

            UtilityBills bill = utilityBillsHandler.updateValues(new UtilityBills(), type, value, cost);

            bill.setMonth(month);
            bill.setYear(year);
            bill.setUpdateDate(new Date());

            utilityBillsRepository.save(bill);

            return;
        }

        utilityBillsRepository.save(utilityBillsHandler.updateValues(utilityBills.get(), type, value, cost));
    }
}
