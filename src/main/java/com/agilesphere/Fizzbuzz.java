package com.agilesphere;

import java.util.function.Predicate;

public class FizzBuzz {

    private static final Predicate<Integer> DIVISIBLE_BY_3 = i -> i % 3 == 0;
    private static final String FIZZ = "fizz";

    private final int from;
    private final int to;

    private FizzBuzz(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public static FizzBuzz of(int from, int to) {
        return new FizzBuzz(from, to);
    }

    public static FizzBuzz to(int to) {
        return new FizzBuzz(1, to);
    }

    public String output() {
        StringBuilder sb = new StringBuilder();
        for (int i = from; i <= to; i++) {
            sb.append(convert(i));
            sb.append(addSeparator(i));
        }
        return sb.toString();
    }

    private String convert(int i) {
        if (DIVISIBLE_BY_3.test(i)) return FIZZ;
        return String.valueOf(i);
    }

    private String addSeparator(int i) {
        return i < to ? " " : "";
    }

}

