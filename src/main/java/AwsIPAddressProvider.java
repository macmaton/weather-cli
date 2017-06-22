import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class AwsIPAddressProvider implements IPAddressProvider {
    public String getIP() {
        InputStream stream = null;
        String ip = null;

        try{
            URL ipCheck = new URL("http://checkip.amazonaws.com/");
            stream = ipCheck.openStream();
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            ip = s.hasNext() ? s.next() : "";
        } catch (MalformedURLException e) {
            System.out.println("Improper url found for IPAddressProvider");
        } catch (IOException e) {
            System.out.println("Unable to reach external IPAddress service");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    //ignore - stream did not close gracefully, but does not affect user
                }
            }
        }

        return ip;
    }
}
