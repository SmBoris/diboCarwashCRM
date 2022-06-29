package carwash.dibo.service;

import carwash.dibo.bills.UtilityMeterType;
import carwash.dibo.model.UtilityBills;

import java.time.Month;
import java.util.List;

public interface UtilityBillsService {

    List<UtilityBills> getLast7Rows();

    void save(UtilityMeterType type, int month, int value, int cost);
}
