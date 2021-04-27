package com.ndrewcoding.world;

public class Camera {

    public static int x = 0;
    public static int y = 0;

    public static int clamp(int actual, int min, int max) {
        if (actual < min) {
            actual = min;
        }

        if (actual > max) {
            actual = max;
        }

        return actual;
    }

}
