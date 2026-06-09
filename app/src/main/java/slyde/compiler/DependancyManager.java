package slyde.compiler;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import slyde.App;
import slyde.generation.LLVMGeneratorVersionTwo;
import slyde.utils.ErrorHandler;

public class DependancyManager {

    private static final List<String> dependancy = new ArrayList<>();
    private static final List<Boolean> added = new ArrayList<>();
    static {
        for (int i = 0; i < 4; i++) {
            added.add(false);
        }
    }

    public static void requireGlfw() {
        if (added.get(0)) {
            return;
        }

        Path glfwLib = NativeUtils.extractResource("/native/glfw/build/src/libglfw.a");

        dependancy.add(glfwLib.toString());

        switch (App.operatingSystem) {
            case MAC:
                dependancy.add("\\");
                dependancy.add("-framework Cocoa");
                dependancy.add("-framework OpenGL");
                dependancy.add("-framework IOKit");
                break;
            case LINUX:
                dependancy.add("-lX11");
                dependancy.add("-lXrandr");
                dependancy.add("-lpthread");
                dependancy.add("-lXi");
                dependancy.add("-ldl");
                break;
            default:
                ErrorHandler.error("Unsupported Operating System for glfw", new RuntimeException());
                break;
        }

        added.set(0, true);

    }

    public static void requireWindow() {
        if (added.get(1)) {
            return;
        }
        Path myWindow = NativeUtils.extractResource("/predefined/slyde_window.c");
        dependancy.add(myWindow.toString());
        requireGlfw();
        LLVMGeneratorVersionTwo.codemanager.appendHead("declare void @open_window()\n");
        added.set(1, true);
    }

    public static void requireInput() {
        if (added.get(2)) {
            return;
        }
        Path inputImplementation = NativeUtils.extractResource("/predefined/slyde_scanf.c");
        dependancy.add(inputImplementation.toString());
        LLVMGeneratorVersionTwo.codemanager.appendHead("declare i32 @slyde_scanf(i8*, ...)\n");
        added.set(2, true);
    }

    public static void requireOutput() {
        if (added.get(3)) {
            return;
        }
        Path myPrintC = NativeUtils.extractResource("/predefined/slyde_printf.c");
        dependancy.add(myPrintC.toString());
        LLVMGeneratorVersionTwo.codemanager.appendHead("declare i32 @slyde_printf(i8*, ...)\n");
        added.set(3, true);
    }

    public static List<String> compileDependancies() {
        for (String dep : dependancy) {
            System.out.println(dep);
        }
        return dependancy;
    }

}
