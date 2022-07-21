package carwash.dibo.integration.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Weather {

    private double weatherTemperature;
    private String weatherCondition;
    private double windSpeed;

    public Weather(Double weatherTemperature, String weatherCondition, Double windSpeed) {
        this.weatherTemperature = weatherTemperature;
        this.weatherCondition = weatherCondition;
        this.windSpeed = windSpeed;
    }
}
