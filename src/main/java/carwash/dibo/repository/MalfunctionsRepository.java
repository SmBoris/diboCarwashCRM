package carwash.dibo.repository;

import carwash.dibo.model.Malfunctions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MalfunctionsRepository extends JpaRepository<Malfunctions, Long> {

    List<Malfunctions> findAll();

    @Query("SELECT m FROM Malfunctions m WHERE m.isResolved = FALSE")
    List<Malfunctions> findAllByResolvedIsFalse();

    @Query("SELECT b FROM Malfunctions b WHERE b.isResolved = FALSE ORDER BY b.openDate DESC")
    List<Malfunctions> getAllByOpenByDateDesc();

}
