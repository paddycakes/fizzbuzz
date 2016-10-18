package com.agilesphere.utils;

import java.util.Spliterators;
import java.util.function.IntConsumer;

/**
 * An object for traversing and partitioning elements of a given
 * numeric value and base as a sequence of individual digits.
 */
public class DigitSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int initialValue;
    private final int base;
    private int currValue;

    public DigitSpliterator(int number, int base) {
        super(String.valueOf(number).length(), ORDERED | SIZED | NONNULL);
        this.initialValue = number;
        this.base = base;
        this.currValue = initialValue;
    }

    /**
     * Factory method to create a base 10 spliterator.
     * @param number Base 10 Numeric value to be partitioned
     * @return A base 10 spliterator
     */
    public static DigitSpliterator base10Spliterator(int number) {
        return new DigitSpliterator(number, 10);
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
