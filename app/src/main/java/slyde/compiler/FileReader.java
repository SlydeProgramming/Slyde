package slyde.compiler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class FileReader {
    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
