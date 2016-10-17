package com.agilesphere.utils;

import java.util.Spliterator;
import java.util.function.IntConsumer;

public class DigitSpliterator implements Spliterator.OfInt {

    private final int initialValue;
    private final int base;
    private int currValue;

    public DigitSpliterator(int number, int base) {
        this.initialValue = number;
        this.base = base;
        this.currValue = initialValue;
    }

    public static DigitSpliterator base10Spliterator(int number) {
        return new DigitSpliterator(number, 10);
    }

    @Override
    public OfInt trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return String.valueOf(initialValue).length();
    }

    @Override
    public int characteristics() {
        return 0;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (currValue == 0) return false;

        int digit = nextDigit();
        currValue /= base;
        action.accept(digit);
        return true;
    }

    private int nextDigit() {
        return currValue % base;
    }


}
