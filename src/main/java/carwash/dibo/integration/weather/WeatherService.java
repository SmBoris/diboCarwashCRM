package carwash.dibo.integration.weather;

import org.json.JSONException;

import java.io.IOException;

public interface WeatherService {
    double getCurrentTemperature() throws InterruptedException, IOException, JSONException;
    Weather getCurrentWeather();
    void saveCurrentHistory(Weather weather);
    void clearCurrentHistory();

    String getDailyAverageCondition();
    double getDailyAverageTemp();
    double getDailyAverageWindSpeed();

}
