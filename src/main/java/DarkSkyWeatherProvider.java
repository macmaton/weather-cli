import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

public class DarkSkyWeatherProvider implements WeatherProvider {
    private String key;

    public DarkSkyWeatherProvider() {
        loadKey();
    }

    public Weather getCurrentWeather(Location location) {
        JSONObject current = new JSONObject(getForecast(location).get("currently").toString());
        int time = Integer.valueOf(current.get("time").toString());
        String summary = current.get("summary").toString();
        Float realTemperature = Float.valueOf(current.get("temperature").toString());
        Float apparentTemperature = Float.valueOf(current.get("apparentTemperature").toString());
        String precipType = current.toString().contains("precipType") ? current.get("precipType").toString
                () : null;
        Float precipIntensity = Float.valueOf(current.get("precipIntensity").toString());
        Float precipProbability = Float.valueOf(current.get("precipProbability").toString());
        Float humidity = Float.valueOf(current.get("humidity").toString());

        return new Weather(time, summary, realTemperature, apparentTemperature, precipType, precipIntensity,
                precipProbability, humidity);
    }

    public List<Weather> getHourlyWeather(Location location) {
        return null;
    }

    public List<Weather> getDailyWeather(Location location) {
        return null;
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
