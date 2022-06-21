package carwash.dibo.impl;

import carwash.dibo.model.UtilityBills;
import carwash.dibo.repository.UtilityBillsRepository;
import carwash.dibo.service.UtilityBillsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UtilityBillsServiceImpl implements UtilityBillsService {

    private final UtilityBillsRepository utilityBillsRepository;

    @Override
    public List<UtilityBills> getLast4Rows() {
        return utilityBillsRepository.findTop4ByOrderByIdDesc();
    }
}
