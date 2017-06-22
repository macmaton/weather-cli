public class Location {
    private float latitude, longitude;
    private String city, region, country, postalCode;

    /**
     * @param latitude required
     * @param longitude required
     * @param region the region (such as state or province)
     */
    public Location(float latitude, float longitude, String city, String region, String country, String postalCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.region = region;
        this.country = country;
        this.postalCode = postalCode;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
