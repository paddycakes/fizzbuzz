package com.agilesphere;

import com.google.common.base.Objects;

import java.util.Optional;
import java.util.function.Predicate;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Arrays.asList;

public class FizzBuzz {

    private static final Predicate<Integer> DIVISIBLE_BY_3 = i -> i % 3 == 0;
    private static final Predicate<Integer> IS_NEGATIVE = i -> i < 0;

    private static final String FIZZ = "fizz";

    private final int from;
    private final int to;

    private Integer hashCode;
    private String asString;

    private FizzBuzz(int from, int to) {
        checkArgument(allPositive(from, to), "Input(s) cannot be negative");
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

    private boolean allPositive(Integer... values) {
        Optional<Integer> anyNegative =
                asList(values).stream()
                        .filter(IS_NEGATIVE)
                        .findFirst();
        return !anyNegative.isPresent();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FizzBuzz)) return false;
        FizzBuzz that = (FizzBuzz) obj;
        return (this.from == that.from)
                && (this.to == that.to);
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hashCode(from, to);
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (asString == null) {
            asString = toStringHelper(getClass())
                    .add("from", from)
                    .add("to", to)
                    .toString();
        }
        return asString;
    }

}

