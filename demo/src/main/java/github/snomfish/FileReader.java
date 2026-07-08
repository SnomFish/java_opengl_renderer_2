package github.snomfish;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileReader {
    

    public static String read(String filePath) {
        try {

            InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new RuntimeException("File not found in resources: " + filePath);
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read file at " + filePath, e);
        }
    }
}
