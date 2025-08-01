package utils;

public class TextUtils {
    public static boolean isValidText(String input) {
        return input.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s.,\\-()]+");
    }
}
