package carwash.dibo.integration.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Getter
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final List<Weather> dailyWeathers;
    private final WeatherProvider weatherProvider;
    private static Temporal updatedTime = LocalTime.now();

    @Override
    public double getCurrentTemperature() {
        return getCurrentWeather().getWeatherTemperature();
    }

    @Override
    public Weather getCurrentWeather(){
        return weatherProvider.getCurrentWeather();
    }

    @Override
    public void saveCurrentHistory(Weather weather){
        Temporal localDate = LocalTime.now();
        long hours = ChronoUnit.HOURS.between(updatedTime, localDate);
        if (hours < 3){
            return;
        }

        updatedTime = localDate;
        dailyWeathers.add(weather);
    }

    @Override
    public void clearCurrentHistory(){ dailyWeathers.clear();}

    @Override
    public String getDailyAverageCondition() {

        if (dailyWeathers == null || dailyWeathers.size() < 1){
            return "Не определено";
        }

        List<String> duplicates =
                dailyWeathers.stream()
                        .flatMap(w -> Stream.of(w.getWeatherCondition()))
                        .collect(Collectors.toList())
                        .stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .filter(p -> p.getValue() > 1)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

        if (duplicates.size() == 0) {
            return dailyWeathers.get(0).getWeatherCondition();
        }

        return duplicates.get(0);
    }

    @Override
    public double getDailyAverageTemp(){
        double averageTemp = 0;

        for (Weather weather : dailyWeathers){
            averageTemp += weather.getWeatherTemperature();
        }

        return averageTemp / dailyWeathers.size();
    }

    @Override
    public double getDailyAverageWindSpeed(){
        double averageSpeed = 0;

        for (Weather weather : dailyWeathers){
            averageSpeed += weather.getWindSpeed();
        }

        return averageSpeed / dailyWeathers.size();
    }
}
