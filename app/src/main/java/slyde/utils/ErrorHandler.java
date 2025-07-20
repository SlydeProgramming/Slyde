package slyde.utils;

import slyde.App;

public class ErrorHandler {

    public static void warn(String message, int l, int c) {
        System.out.println(
                "\033[33m" + message + "\n\nat " + App.target.toString() + ":" + l + ":" + (c + 1) + "\n" + "\033[0m");
    }

    public static void error(String message, int l, int c) {
        System.out.println(
                "\033[31m" + message + "\n\nat " + App.target.toString() + ":" + l + ":" + (c + 1) + "\n" + "\033[0m");
        System.err.println(
                "\033[31m" + message + "\n\nat " + App.target.toString() + ":" + l + ":" + (c + 1) + "\n" + "\033[0m");
        throw new Error();
    }

    public static void error(String message, int l) {
        System.out.println(
                "\033[31m" + message + "\n\nat " + App.target.toString() + ":" + l + "\n" + "\033[0m");
        System.err.println(
                "\033[31m" + message + "\n\nat " + App.target.toString() + ":" + l + "\n" + "\033[0m");
        throw new Error();
    }

    public static void error(String message, Throwable cause) {
        System.out.println(
                "\033[31m" + message + "\n" + "\033[0m");
        System.err.println(
                "\033[31m" + message + "\n" + "\033[0m");
        throw new Error("", cause);
    }
}
