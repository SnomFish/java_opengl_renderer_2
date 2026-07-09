package github.snomfish;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileManager {
    

    public static String read(String filePath) {
        try {

            InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(filePath);

            if (is == null) {
                throw new RuntimeException("File not found in resources: " + filePath);
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read file at " + filePath, e);
        }
    }


    @SuppressWarnings("CallToPrintStackTrace")
    public static <T> T parse(String filePath, Class<T> clazz) {
        try {

            InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(filePath);

            if (is == null) {
                throw new FileNotFoundException("file " + filePath + " not found.");
            }

            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(is, clazz);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @SuppressWarnings("CallToPrintStackTrace")
    public static <T> void write(String filePath, T object) {
        try {

            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(new File(filePath), object);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
