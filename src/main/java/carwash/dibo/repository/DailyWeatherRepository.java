package carwash.dibo.repository;

import carwash.dibo.model.DailyWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyWeatherRepository extends JpaRepository<DailyWeather, Long> {
}
