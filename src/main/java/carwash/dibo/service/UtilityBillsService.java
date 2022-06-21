package carwash.dibo.service;

import carwash.dibo.model.UtilityBills;

import java.util.List;

public interface UtilityBillsService {
    List<UtilityBills> getLast4Rows();
}
