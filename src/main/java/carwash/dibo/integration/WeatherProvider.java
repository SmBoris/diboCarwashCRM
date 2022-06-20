package carwash.dibo.integration;

import carwash.dibo.common.Weather;
import carwash.dibo.utils.Downloader;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
@Slf4j
public class WeatherProvider {
    private static long lastCallTime = -1;
    private static final long DELAY_BETWEEN_CALLS = 120;
    private static final String LATITUDE = "59.97837376751";
    private static final String LONGITUDE = "30.374221259214327";
    private static final String UNITS_OF_MEASUREMENT = "metric";
    private static final String LANGUAGE = "ru";
    private static final String URL_API = "https://api.openweathermap.org/data/2.5/weather?lat=" + LATITUDE + "&lon="
        + LONGITUDE + "&appid=";

    private static String API_KEY;

    public WeatherProvider(@Value("${weather.apikey}") String api_key) {
        API_KEY = api_key;
    }

    private static JSONObject callApiMethod() throws IOException, JSONException, InterruptedException {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCallTime < DELAY_BETWEEN_CALLS) {
            Thread.sleep(Math.max(DELAY_BETWEEN_CALLS, DELAY_BETWEEN_CALLS - (currentTime - lastCallTime)));
        }
        lastCallTime = System.currentTimeMillis();
        String url = URL_API + API_KEY + "&units=" + UNITS_OF_MEASUREMENT + "&lang=" + LANGUAGE;
        String text = Downloader.downloadText(url);

        return new JSONObject(text);
    }

    public static Weather currentWeather() {
        try {
            JSONObject obj = callApiMethod();
            JSONObject main = obj.getJSONObject("main");
            JSONArray weather = obj.getJSONArray("weather");
            JSONObject cond = weather.getJSONObject(0);
            JSONObject wind = obj.getJSONObject("wind");
            return new Weather(main.getDouble("temp"), cond.getString("description"), wind.getDouble("speed"));
        }
        catch (Exception e){
            log.warn("Can't parse weather for date: " + LocalDate.now().toString());
        }

        return new Weather(0.00, "не определено", 0.00);
    }
}
