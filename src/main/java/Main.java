public class Main {

    public static void main(String[] args) {
        IPAddressProvider ipProvider = new AwsIPAddressProvider();
        LocationProvider locationProvider = new GeoLiteLocationProvider();
        Location location = locationProvider.getLocation(ipProvider.getIP());
        WeatherProvider weatherProvider = new DarkSkyWeatherProvider();
        Weather current = weatherProvider.getCurrentWeather(location);
        System.out.println(location.getCity() + ", " + location.getRegion());
        System.out.println(current.toString());
    }


}
