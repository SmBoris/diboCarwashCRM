package carwash.dibo.service;

import carwash.dibo.model.Expense;

import java.util.List;

public interface ExpenseService {

    void save(Expense expense);

    List<Expense> getLast5Rows();
}
