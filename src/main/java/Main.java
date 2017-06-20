
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static String key;

    public static void main(String[] args) {
        loadKey();
        Location location = getLocation();
        URL forecastUrl = getForecastURL(key, location.latitude, location.longitude);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) forecastUrl.openConnection();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String response = reader.readLine();
            reader.close();
            JSONObject raw = new JSONObject(response);
            JSONObject currently = new JSONObject(raw.get("currently").toString());
            System.out.println("City: " + location.city);
            System.out.println("It is currently " + currently.get("apparentTemperature") + " degrees and " +
                    currently.get("summary").toString().toLowerCase() + ".");
        } catch (IOException e) {
            //TODO error message
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String getIP() {
        InputStream stream = null;
        String ip = null;

        try{
            URL ipCheck = new URL("http://checkip.amazonaws.com/");
            stream = ipCheck.openStream();
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            ip = s.hasNext() ? s.next() : "";
        } catch (MalformedURLException e) {
            //TODO error message
            e.printStackTrace();
        } catch (IOException e) {
            //TODO error message
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    //TODO error message
                    e.printStackTrace();
                }
            }
        }

        return ip;
    }

    private static Location getLocation() {
        Location location = null;

        try {
            LookupService geoService = new LookupService("/home/margaret/bin/geolite/GeoLiteCity.dat");
            location = geoService.getLocation(getIP());
        } catch (IOException e) {
            //TODO error message
            e.printStackTrace();
        }

        return location;
    }

    private static URL getForecastURL(String key, float latitude, float longitude) {
        URL result = null;
        String path = key + "/" + latitude + "," + longitude;

        try {
            result = new URL("https://api.darksky.net/forecast/" + path);
        } catch (MalformedURLException e) {
            //TODO error message
            e.printStackTrace();
        }

        return result;
    }

    private static void loadKey() {
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
                    //TODO error message
                    e.printStackTrace();
                }
            }
        }

    }
}
