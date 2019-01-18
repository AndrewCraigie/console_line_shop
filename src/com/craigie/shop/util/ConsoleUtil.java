package com.craigie.shop.util;

public class ConsoleUtil {

    public final static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }

    public static final String LS = System.lineSeparator();

    public static String fixedLengthString(String string, int length) {

        return String.format("%1$-" + length + "s", string);

    }


}
