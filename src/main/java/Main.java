import java.util.List;

public class Main {

    public static void main(String[] args) {
        IPAddressProvider ipProvider = new AwsIPAddressProvider();
        LocationProvider locationProvider = new GeoLiteLocationProvider();
        Location location = locationProvider.getLocation(ipProvider.getIP());
        WeatherProvider weatherProvider = new DarkSkyWeatherProvider();
        Weather current = weatherProvider.getCurrentWeather(location);
        System.out.println(location.getCity() + ", " + location.getRegion());

        System.out.println("\nCurrent Weather");
        System.out.println(current.toString());

        System.out.println("=======================================");
        System.out.println("Daily Weather");
        List<Weather> daily = weatherProvider.getDailyWeather(location);
        for (int i = 0; i < 7; i++) {
            System.out.println(daily.get(i).toString());
        }
    }


}
