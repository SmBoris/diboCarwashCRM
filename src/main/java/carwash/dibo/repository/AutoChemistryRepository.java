package carwash.dibo.repository;

import carwash.dibo.model.AutoChemistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoChemistryRepository extends JpaRepository<AutoChemistry, Long> {

    @Query(value = "SELECT a FROM AutoChemistry a ORDER BY a.id DESC")
    List<AutoChemistry> getAutoChemicalByDesc();
}
