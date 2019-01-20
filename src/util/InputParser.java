package util;

import java.util.regex.Pattern;

public class InputParser {

    public static String[] parseBasketInput(String input){

        String[] parsed = input.split(":");

        return parsed;
    }

    public static boolean validQuantityValue(String input){

        // positive or negative integers but not zero
        return Pattern.matches("-?[1-9]\\d*", input);

    }

    public static boolean isMenuDigit(String input){

        // true if value is 0, 1, 2, 3, 4, or 5
        return Pattern.matches("^([0-5])$", input);

    }

}
