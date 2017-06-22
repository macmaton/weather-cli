import java.text.SimpleDateFormat;

public class DailyWeather extends Weather {
    private Float minTemperature, maxTemperature;

    public DailyWeather(int time, String summary, Float minTemperature, Float maxTemperature, String precipType,
                        Float precipIntensity, Float precipProbability, Float humidity) {
        super(time, summary, null, null, precipType, precipIntensity, precipProbability, humidity);
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    private String getTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        String datetime = sdf.format(time*1000L);
        return datetime;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Time: ");
        result.append(getTimeString());
        result.append("\n");

        result.append("Condition: ");
        result.append(summary);
        result.append("\n");

        result.append("Low Temperature: ");
        result.append(String.valueOf(minTemperature));
        result.append("\u00b0\n");

        result.append("High Temperature: ");
        result.append(String.valueOf(maxTemperature));
        result.append("\u00b0\n");

        result.append("Chance of precipitation: ");
        result.append(String.valueOf(Math.round(precipProbability * 100)));
        result.append("%\n");

        if (precipType != "none") {
            result.append("Precipitation intensity: ");
            result.append(String.valueOf(Math.round(precipIntensity * 100)));
            result.append("%\n");

            result.append("Precipitation type: ");
            result.append(precipType);
            result.append("\n");
        }

        result.append("Humidity: ");
        result.append(String.valueOf(Math.round(humidity * 100)));
        result.append("%\n");

        return result.toString();
    }
}
