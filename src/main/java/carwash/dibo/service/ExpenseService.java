package carwash.dibo.service;

import carwash.dibo.model.AdditionalExpense;

import java.util.List;

public interface ExpenseService {
    void save(AdditionalExpense expense);

    List<AdditionalExpense> getLast5Rows();
}
