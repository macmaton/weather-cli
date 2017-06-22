import com.sun.xml.internal.bind.v2.TODO;

import java.text.SimpleDateFormat;

public class Weather {
    int time; //UNIX time
    String summary, precipType;
    Float realTemperature, apparentTemperature, precipIntensity, precipProbability, humidity;

    public Weather(int time, String summary, Float realTemperature, Float apparentTemperature, String precipType,
                   Float precipIntensity, Float precipProbability, Float humidity) {
        this.time = time;
        this.summary = summary;
        this.precipType = precipType;
        this.realTemperature = realTemperature;
        this.apparentTemperature = apparentTemperature;
        this.precipIntensity = precipIntensity;
        this.precipProbability = precipProbability;
        this.humidity = humidity;
    }

    public int getTime() {
        return time;
    }

    public String getTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
        String datetime = sdf.format(time*1000L);
        return datetime;
    }

    public String getSummary() {
        return summary;
    }

    public String getPrecipType() {
        return precipType;
    }

    public Float getRealTemperature() {
        return realTemperature;
    }

    public Float getApparentTemperature() {
        return apparentTemperature;
    }

    public Float getPrecipIntensity() {
        return precipIntensity;
    }

    public Float getPrecipProbability() {
        return precipProbability;
    }

    public Float getHumidity() {
        return humidity;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Time: ");
        result.append(getTimeString());
        result.append("\n");

        result.append("Condition: ");
        result.append(summary);
        result.append("\n");

        result.append("Actual temperature: ");
        result.append(String.valueOf(realTemperature));
        result.append("\u00b0\n");

        result.append("Feels like: ");
        result.append(String.valueOf(apparentTemperature));
        result.append("\u00b0\n");

        result.append("Chance of precipitation: ");
        result.append(String.valueOf(Math.round(precipProbability * 100)));
        result.append("%\n");

        result.append("Precipitation intensity: ");
        result.append(String.valueOf(Math.round(precipIntensity * 100)));
        result.append("%\n");

        if (precipType != null) {
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
