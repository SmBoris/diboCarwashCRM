package carwash.dibo.integration.weather;


import carwash.dibo.model.DailyWeather;

public interface DailyWeatherService {
    void updateDailyWeather(double averageTemp, double averageWindSpeed, String averageCondition);
    void save(DailyWeather dailyWeather);
}
