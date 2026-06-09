package slyde.compiler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import slyde.utils.ErrorHandler;

public class NativeUtils {

  public static Path extractResource(String resourcePath) {
    try {
      InputStream in = NativeUtils.class.getResourceAsStream(resourcePath);
      if (in == null)
        throw new FileNotFoundException("Resource not found: " + resourcePath);

      Path tempFile = Files.createTempFile("slyde_native_", ".c");
      tempFile.toFile().deleteOnExit();

      Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
      return tempFile;

    } catch (Exception e) {
      ErrorHandler.error(resourcePath, e);
      return null;
    }

  }

  public static void downloadAndBuildGLFW() throws IOException, InterruptedException {
    String os = System.getProperty("os.name").toLowerCase();
    ProcessBuilder pb;

    if (os.contains("mac")) {
      pb = new ProcessBuilder("sh", "-c",
          "curl -L https://github.com/glfw/glfw/releases/download/3.4/glfw-3.4.zip -o glfw.zip && " +
              "unzip glfw.zip && cd glfw-3.4 && cmake . && make && sudo make install");
    } else if (os.contains("linux")) {
      pb = new ProcessBuilder("sh", "-c",
          "wget https://github.com/glfw/glfw/releases/download/3.4/glfw-3.4.tar.gz && " +
              "tar -xzf glfw-3.4.tar.gz && cd glfw-3.4 && cmake . && make && sudo make install");
    } else if (os.contains("win")) {
      pb = new ProcessBuilder("powershell", "-Command",
          "curl -L -o glfw.zip https://github.com/glfw/glfw/releases/download/3.4/glfw-3.4.bin.WIN64.zip; " +
              "tar -xf glfw.zip");
    } else {
      throw new RuntimeException("Unsupported OS for auto GLFW install");
    }

    pb.inheritIO();
    Process p = pb.start();
    int exitCode = p.waitFor();
    if (exitCode != 0)
      throw new RuntimeException("GLFW download/build failed");
  }

}
