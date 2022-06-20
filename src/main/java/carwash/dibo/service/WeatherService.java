package carwash.dibo.service;

import org.json.JSONException;

import java.io.IOException;

public interface WeatherService {
    double getCurrentTemperature() throws InterruptedException, IOException, JSONException;
}
