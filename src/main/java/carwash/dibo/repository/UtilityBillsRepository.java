package carwash.dibo.repository;

import carwash.dibo.model.UtilityBills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilityBillsRepository extends JpaRepository<UtilityBills, Long> {

    List<UtilityBills> findTop7ByOrderByIdDesc();

    @Query("SELECT u FROM UtilityBills u " +
            "WHERE u.month = :#{#month} ")

    Optional<UtilityBills> getByMonthAndYear (@Param("month") int month);
}
