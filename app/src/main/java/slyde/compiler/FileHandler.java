package slyde.compiler;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static void writeFile(String filePath, String val) throws IOException {
        FileWriter file = new FileWriter(filePath);
        file.write(val);
        file.close();
    }

    public static void exec(String command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
        processBuilder.inheritIO();
        Process p = processBuilder.start();
        p.waitFor();

    }

}
