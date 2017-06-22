import java.util.List;

/**
 * Created by margaret on 6/22/17.
 */
public interface WeatherProvider {
    Weather getCurrentWeather(Location location);
    List<Weather> getHourlyWeather(Location location);
    List<Weather> getDailyWeather(Location location);
}
