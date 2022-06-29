package carwash.dibo.impl;

import carwash.dibo.model.Expense;
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
    public void save(Expense expense) {
        additionalExpenseRepository.save(expense);
    }

    @Override
    public List<Expense> getLast5Rows() {
        return additionalExpenseRepository.findTop5ByOrderByIdDesc();
    }
}
