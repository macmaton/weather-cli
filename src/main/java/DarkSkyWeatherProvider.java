import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DarkSkyWeatherProvider implements WeatherProvider {
    private String key;

    public DarkSkyWeatherProvider() {
        loadKey();
    }

    public Weather getCurrentWeather(Location location) {
        JSONObject current = getForecast(location).getJSONObject("currently");
        return getWeather(current);
    }

    public List<Weather> getHourlyWeather(Location location) {
        List<Weather> forecasts = new ArrayList<Weather>();
        JSONArray hourly = getForecast(location).getJSONObject("hourly").getJSONArray("data");
        for (Object h : hourly) {
            JSONObject forecast = (JSONObject) h;
            forecasts.add(getWeather(forecast));
        }
        return forecasts;
    }

    public List<Weather> getDailyWeather(Location location) {
        List<Weather> forecasts = new ArrayList<Weather>();
        JSONArray daily = getForecast(location).getJSONObject("daily").getJSONArray("data");
        for (Object d : daily) {
            JSONObject forecast = (JSONObject) d;
            forecasts.add(getWeather(forecast));
        }
        return forecasts;
    }

    /**
     * Creates Weather object from JSON object.  Assumes JSON object is for a single time period
     * @return Weather object representing given forecast
     */
    private Weather getWeather(JSONObject forecast) {
        Weather weather;

        int time = Integer.valueOf(forecast.get("time").toString());
        String summary = forecast.has("summary") ? forecast.get("summary").toString() : null;
        Float realTemperature = forecast.has("temperature") ? Float.valueOf(forecast.get("temperature").toString())
                : null;
        Float apparentTemperature = forecast.has("apparentTemperature") ? Float.valueOf(forecast.get
                ("apparentTemperature").toString()) : null;
        String precipType = forecast.has("precipType") ? forecast.get("precipType").toString
                () : null;
        Float precipIntensity = forecast.has("precipIntensity") ? Float.valueOf(forecast.get("precipIntensity")
                .toString()) : null;
        Float precipProbability = forecast.has("precipProbability") ? Float.valueOf(forecast.get("precipProbability")
                .toString
                ()) : null;
        Float humidity = forecast.has("humidity") ? Float.valueOf(forecast.get("humidity").toString()) : null;

        if (forecast.has("temperatureMin") || forecast.has("temperatureMax")) {
            Float minTemperature = forecast.has("temperatureMin") ? Float.valueOf(forecast.get("temperatureMin")
                    .toString())
                    : null;
            Float maxTemperature = forecast.has("temperatureMax") ? Float.valueOf(forecast.get("temperatureMax")
                    .toString())
                    : null;
            weather = new DailyWeather(time, summary, minTemperature, maxTemperature, precipType, precipIntensity,
                    precipProbability, humidity);
        } else {
            weather = new Weather(time, summary, realTemperature, apparentTemperature, precipType, precipIntensity,
                    precipProbability, humidity);
        }

        return weather;
    }

    private JSONObject getForecast(Location location) {
        JSONObject forecast = null;
        URL forecastUrl = getForecastURL(location.getLatitude(), location.getLongitude());
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) forecastUrl.openConnection();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String response = reader.readLine();
            reader.close();
            forecast = new JSONObject(response);
        } catch (IOException e) {
            System.out.println("Unable to retrieve weather data");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return forecast;
    }

    private void loadKey() {
        Properties config = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            config.load(input);
            key = config.getProperty("key").trim();
        } catch (FileNotFoundException e) {
            System.out.println("config.properties file not found");
        } catch (IOException e) {
            System.out.println("Unable to load configuration");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    //ignore - error encountered while closing stream; does not affect user.
                }
            }
        }

    }

    private URL getForecastURL(float latitude, float longitude) {
        URL result = null;
        String path = key + "/" + latitude + "," + longitude;

        try {
            result = new URL("https://api.darksky.net/forecast/" + path);
        } catch (MalformedURLException e) {
            System.out.println("Failed to generate forecast request URL");
        }

        return result;
    }
}
