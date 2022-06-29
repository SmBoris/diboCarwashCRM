package carwash.dibo.repository;

import carwash.dibo.model.WorkingDay;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Long> {

    List<WorkingDay> findAll();
    List<WorkingDay> findByOpenTrue();

    WorkingDay findTopByOrderByIdDesc();
    List<WorkingDay> findTop7ByOrderByIdDesc();
}
