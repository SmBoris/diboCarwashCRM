package carwash.dibo.integration.weather;

import carwash.dibo.model.DailyWeather;
import carwash.dibo.repository.DailyWeatherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DailyWeatherServiceImpl implements DailyWeatherService {
    private final DailyWeather dailyWeather;
    private final DailyWeatherRepository dailyWeatherRepository;

    @Override
    public void updateDailyWeather(double averageTemp, double averageWindSpeed, String averageCondition) {
        dailyWeather.setAverageTemp(averageTemp);
        dailyWeather.setAverageWindSpeed(averageWindSpeed);
        dailyWeather.setAverageCondition(averageCondition);
    }

    @Override
    public void save(DailyWeather dailyWeather) {
        dailyWeatherRepository.save(dailyWeather);
    }

}
