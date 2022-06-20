package carwash.dibo.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Weather {

    private double weatherTemperature;
    private String weatherCondition;
    private Double windSpeed;
    private List<Weather> weathers;

    public Weather(Double weatherTemperature, String weatherCondition, Double windSpeed) {
        this.weatherTemperature = weatherTemperature;
        this.weatherCondition = weatherCondition;
        this.windSpeed = windSpeed;
    }
}
