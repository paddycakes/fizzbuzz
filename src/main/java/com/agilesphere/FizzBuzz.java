package com.agilesphere;

import com.agilesphere.rules.OverrideRule;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.joining;

public class FizzBuzz {

    private static final IntPredicate DIVISIBLE_BY_3 = i -> i % 3 == 0;
    private static final IntPredicate DIVISIBLE_BY_5 = i -> i % 5 == 0;
    private static final IntPredicate DIVISIBLE_BY_3_AND_5 = DIVISIBLE_BY_3.and(DIVISIBLE_BY_5);
    private static final IntPredicate IS_NEGATIVE = i -> i < 0;

    private static final String FIZZ = "fizz";
    private static final String BUZZ = "buzz";
    private static final String FIZZBUZZ = FIZZ + BUZZ;
    private static final String SPACE = " ";

    private final int from;
    private final int to;
    private final List<OverrideRule> overrideRules;
    private String output;

    private Integer hashCode;
    private String asString;

    private FizzBuzz(int from, int to, List<OverrideRule> overrideRules) {
        checkArgument(allPositive(from, to), "Input(s) cannot be negative");
        checkArgument(inAscendingOrder(from, to), "'from' cannot be bigger than 'to");
        this.from = from;
        this.to = to;
        this.overrideRules = overrideRules;
    }

    public String output() {
        if (output == null) {
            output = IntStream.rangeClosed(from, to)
                    .mapToObj(this::convert)
                    .collect(joining(SPACE));
        }
        return output;
    }

    private String convert(int i) {
        if (hasOverrideRules()) {
            for (OverrideRule rule : overrideRules) {
                if (rule.matches(i)) {
                    return rule.result();
                }
            }
        }
        if (DIVISIBLE_BY_3_AND_5.test(i)) return FIZZBUZZ;
        if (DIVISIBLE_BY_3.test(i)) return FIZZ;
        if (DIVISIBLE_BY_5.test(i)) return BUZZ;
        return String.valueOf(i);
    }

    private boolean allPositive(int... values) {
        OptionalInt anyNegative =
                IntStream.of(values)
                        .filter(IS_NEGATIVE)
                        .findFirst();
        return !anyNegative.isPresent();
    }

    private boolean inAscendingOrder(int from, int to) {
        return from <= to;
    }

    private boolean hasOverrideRules() {
        return !overrideRules.isEmpty();
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

    public static class Builder {

        private int from = 1;
        private int to = 20;
        private List<OverrideRule> rules = new ArrayList<>();

        public Builder() {}

        public Builder from(int from) {
            this.from = from;
            return this;
        }

        public Builder to(int to) {
            this.to = to;
            return this;
        }

        public Builder withOverrideRule(OverrideRule rule) {
            rules.add(rule);
            return this;
        }

        public FizzBuzz build() {
            return new FizzBuzz(from, to, rules);
        }
    }

}

