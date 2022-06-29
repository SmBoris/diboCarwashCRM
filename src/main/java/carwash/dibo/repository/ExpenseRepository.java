package carwash.dibo.repository;

import carwash.dibo.model.AdditionalExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<AdditionalExpense, Long> {
    List<AdditionalExpense> findTop5ByOrderByIdDesc();
}
