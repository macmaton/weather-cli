import com.maxmind.geoip.*;

import java.io.IOException;

public class GeoLiteLocationProvider implements LocationProvider {
    public Location getLocation(String ipAddress) {
        Location location = null;

        try {
            LookupService geoService = new LookupService("/home/margaret/bin/geolite/GeoLiteCity.dat");
            com.maxmind.geoip.Location geolite = geoService.getLocation(ipAddress);
            location = new Location(geolite.latitude, geolite.longitude, geolite.city, geolite.region, geolite
                    .countryCode, geolite.postalCode);
        } catch (IOException e) {
            System.out.println("Location database not found");
        }

        return location;
    }
}