package slyde.compiler;

import java.io.*;
import java.nio.file.*;

public class NativeUtils {

    public static Path extractResource(String resourcePath) throws IOException {
        InputStream in = NativeUtils.class.getResourceAsStream(resourcePath);
        if (in == null)
            throw new FileNotFoundException("Resource not found: " + resourcePath);

        Path tempFile = Files.createTempFile("slyde_native_", ".c");
        tempFile.toFile().deleteOnExit();

        Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile;
    }
}
