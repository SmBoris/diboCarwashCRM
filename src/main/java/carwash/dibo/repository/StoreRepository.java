package carwash.dibo.repository;

import carwash.dibo.model.StoreQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<StoreQuantity, Long> {
    Optional<StoreQuantity> findOneByName(String name);
}
