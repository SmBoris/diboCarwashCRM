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

    private final ExpenseRepository expenseRepository;

    @Override
    public void save(Expense expense) {
        expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getLast5Rows() {
        return expenseRepository.findTop5ByOrderByIdDesc();
    }
}
