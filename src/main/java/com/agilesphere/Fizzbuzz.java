package com.agilesphere;

public class Fizzbuzz {

    private Fizzbuzz(int from, int to) {

    }

    public static Fizzbuzz of(int from, int to) {
        return new Fizzbuzz(from, to);
    }

    public static Fizzbuzz to(int to) {
        return new Fizzbuzz(1, to);
    }

    public String output() {
        return "1";
    }
}

