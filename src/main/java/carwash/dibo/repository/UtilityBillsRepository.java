package carwash.dibo.repository;

import carwash.dibo.model.UtilityBills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilityBillsRepository extends JpaRepository<UtilityBills, Long> {

    List<UtilityBills> findTop4ByOrderByIdDesc();

    @Query("SELECT u FROM UtilityBills u " +
            "WHERE u.month = :#{#monthReq} " +
            "AND SUBSTRING(u.dateOfAdd, 7, 2) = :#{#currentDate}")

    Optional<UtilityBills> getByMonthAndYear (@Param("monthReq") Integer month,
                                              @Param("currentDate") @DateTimeFormat(pattern = "yyyy") Date currentDate);
}
