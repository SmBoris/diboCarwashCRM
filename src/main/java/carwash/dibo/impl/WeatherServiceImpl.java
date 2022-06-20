package carwash.dibo.impl;

import carwash.dibo.integration.WeatherProvider;
import carwash.dibo.service.WeatherService;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    @Override
    public double getCurrentTemperature() throws InterruptedException, IOException, JSONException {
        return WeatherProvider.currentWeather().getWeatherTemperature();
    }
}
