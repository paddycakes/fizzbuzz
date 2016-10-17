package com.agilesphere;

public class Fizzbuzz {

    private Fizzbuzz(int from, int to) {

    }

    public static Fizzbuzz of(int from, int to) {
        return new Fizzbuzz(from, to);
    }

    public String output() {
        return "";
    }
}

