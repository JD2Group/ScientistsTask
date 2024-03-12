package org.example.utils;

public class Rnd {

    private Rnd() {
    }

    public static int getRandValue(int a, int b) {
        int min = Math.min(a, b);
        int max = Math.max(a,b);
        return (Constants.RANDOM.nextInt(max - min + 1) + min);
    }
}
