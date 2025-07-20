package slyde;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import slyde.compiler.Compiler;
import slyde.utils.ErrorHandler;

public class App {

    public static String target;

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 2) {
            System.err.println("Usage: slyde <command> <source-file>");
            System.err.println("Commands:");
            System.err.println("  run   <file>   Compile, build, and run the Slyde file");
            System.err.println("  build <file>   Compile and build the Slyde file without running");
            return;
        }

        String command = args[0].toLowerCase();
        Path sourceFile = Paths.get(args[1]).toAbsolutePath();

        target = sourceFile.toString();

        switch (command) {
            case "run":
                compileBuildRun(sourceFile);
                break;
            case "build":
                compileBuild(sourceFile);
                break;
            default:
                System.err.println("Unknown command: " + command);
                System.err.println("Use 'run' or 'build'");
        }
    }

    private static void compileBuildRun(Path sourceFile) throws IOException, InterruptedException {
        Path llvmFile = Paths.get("out.ll").toAbsolutePath();
        Path executable = getExecutablePath();

        Compiler.compile(sourceFile.toString(), llvmFile.toString());

        boolean buildSuccess = runClang(llvmFile, executable);

        if (!buildSuccess) {
            System.err.println("Build failed. Aborting run.");
            return;
        }

        runExecutable(executable);
    }

    private static void compileBuild(Path sourceFile) throws IOException, InterruptedException {
        Path llvmFile = Paths.get("out.ll").toAbsolutePath();
        Path executable = getExecutablePath();

        Compiler.compile(sourceFile.toString(), llvmFile.toString());

        boolean buildSuccess = runClang(llvmFile, executable);

        if (buildSuccess) {
            System.out.println("Build succeeded. Executable is at: " + executable);
        } else {
            System.err.println("Build failed.");
        }
    }

    private static boolean runClang(Path llvmFile, Path outputExe) throws IOException, InterruptedException {
        String clangPath = getClangExecutablePath();

        ProcessBuilder pb = new ProcessBuilder(
                clangPath,
                llvmFile.toString(),
                "-o",
                outputExe.toString());

        pb.inheritIO(); // Forward output/error to console
        Process process = pb.start();
        int exitCode = process.waitFor();

        return exitCode == 0;
    }

    private static void runExecutable(Path executable) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder();

        if (isWindows()) {
            pb.command(executable.toString());
        } else {
            pb.command("./" + executable.getFileName().toString());
        }

        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();
    }

    private static Path getExecutablePath() {
        String execName = isWindows() ? "out.exe" : "out";
        return Paths.get(execName).toAbsolutePath();
    }

    private static String getClangExecutablePath() {
        String execName = isWindows() ? "clang.exe" : "clang";

        try {
            // This gets the path of the running executable (slyde.exe) reliably
            Path exePath = Paths.get(
                    getExecutableLocation()).getParent();

            Path clangPath = exePath.resolve("clang").resolve(execName);

            if (!clangPath.toFile().exists()) {
                throw new RuntimeException("Clang not found at: " + clangPath);
            }

            return clangPath.toString();
        } catch (Exception e) {
            ErrorHandler.error("Failed to locate clang executable", e);
            return null;
        }
    }

    /**
     * Returns the path to the running executable or JAR.
     */
    private static String getExecutableLocation() throws URISyntaxException {
        return getExecutableLocationClass(App.class);
    }

    private static String getExecutableLocationClass(Class<?> cls) throws URISyntaxException {
        // Use class location URL and convert to Path
        return Paths.get(cls.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
