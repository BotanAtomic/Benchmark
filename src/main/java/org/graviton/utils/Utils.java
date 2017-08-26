package org.graviton.utils;

public class Utils {

    public static void Try(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
