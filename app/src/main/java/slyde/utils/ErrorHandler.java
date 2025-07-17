package slyde.utils;

import java.io.IOException;

import slyde.App;
import slyde.generation.LLVMGeneratorVersionTwo;

public class ErrorHandler {

    public static void warn(String message, int l, int c) {
        System.out.println(
                "\033[33m" + message + "\n\nat " + App.target.toString() + ":" + l + ":" + (c + 1) + "\n" + "\033[0m");
    }

    public static void error(String message, int l, int c) {
        try {
            FileHandler.writeFile(App.interrupt.toString(), LLVMGeneratorVersionTwo.codemanager.end());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(
                "\033[31m" + message + "\n\nat " + App.target.toString() + ":" + l + ":" + (c + 1) + "\n" + "\033[0m");
        throw new Error();
    }
}
