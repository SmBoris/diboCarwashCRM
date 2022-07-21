package carwash.dibo.integration.weather;

import carwash.dibo.model.DailyWeather;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class DailyWeatherConfig {

    @Bean
    public DailyWeather getDailyWeather(){
        return new DailyWeather();
    }
}
