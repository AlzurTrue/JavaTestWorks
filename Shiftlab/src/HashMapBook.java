import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HashMapBook {
    public void createHandbook(String filepath, HashMap handbook) throws IOException {

        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(" ", 2);
            if (parts.length >= 2)
            {
                String key = parts[0];
                float value = Float.parseFloat(parts[1]);
                handbook.put(key, value);
            } else {
                System.out.println("ignoring line: " + line);
            }
        }

        reader.close();

    }
}
