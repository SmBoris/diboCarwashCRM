package carwash.dibo.impl;

import carwash.dibo.model.AdditionalExpense;
import carwash.dibo.repository.ExpenseRepository;
import carwash.dibo.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository additionalExpenseRepository;

    @Override
    public void save(AdditionalExpense expense) {
        additionalExpenseRepository.save(expense);
    }

    @Override
    public List<AdditionalExpense> getLast5Rows() {
        return additionalExpenseRepository.findTop5ByOrderByIdDesc();
    }
}
