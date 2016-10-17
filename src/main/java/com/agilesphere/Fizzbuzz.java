package com.agilesphere;

public class FizzBuzz {

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
            sb.append(i);
            sb.append(addSeparator(i));
        }
        return sb.toString();
    }

    private String addSeparator(int i) {
        return i < to ? " " : "";
    }


}

