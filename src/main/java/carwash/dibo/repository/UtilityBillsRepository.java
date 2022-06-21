package carwash.dibo.repository;

import carwash.dibo.model.UtilityBills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilityBillsRepository extends JpaRepository<UtilityBills, Long> {
    List<UtilityBills> findTop4ByOrderByIdDesc();

}
