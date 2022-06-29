package carwash.dibo.impl;

import carwash.dibo.bills.UtilityBillsHandler;
import carwash.dibo.bills.UtilityMeter;
import carwash.dibo.bills.UtilityMeterType;
import carwash.dibo.model.UtilityBills;
import carwash.dibo.repository.UtilityBillsRepository;
import carwash.dibo.service.UtilityBillsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sun.util.resources.LocaleData;

import java.text.DateFormat;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.time.LocalDate;

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

        if (!utilityBills.isPresent()){

            UtilityBills bill = utilityBillsHandler.updateValues(new UtilityBills(), type, value, cost);

            bill.setMonth(month);
            bill.setDateOfAdd(new Date());

            utilityBillsRepository.save(bill);

            return;
        }

        utilityBillsRepository.save(utilityBillsHandler.updateValues(utilityBills.get(), type, value, cost));
    }
}
