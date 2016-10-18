package com.agilesphere;

import com.agilesphere.rules.OverrideRule;
import com.agilesphere.rules.Rules;
import com.google.common.base.Objects;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

/**
 * FizzBuzz ranges over an ascending sequence of numbers replacing:
 *   - any number divisible by three with the word "fizz"
 *   - any number divisible by five with the word "buzz"
 *   - any number divisible by three and five wiht the word "fizzbuzz"
 */
public class FizzBuzz {

    private static final IntPredicate DIVISIBLE_BY_3 = i -> i % 3 == 0;
    private static final IntPredicate DIVISIBLE_BY_5 = i -> i % 5 == 0;
    private static final IntPredicate DIVISIBLE_BY_3_AND_5 = DIVISIBLE_BY_3.and(DIVISIBLE_BY_5);
    private static final IntPredicate IS_NEGATIVE = i -> i <= 0;

    private static final String FIZZ = "fizz";
    private static final String BUZZ = "buzz";
    private static final String FIZZBUZZ = FIZZ + BUZZ;
    private static final String NUMBER = "number";
    private static final String SPACE = " ";
    static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private final int from;
    private final int to;
    private final List<OverrideRule> overrideRules;
    private final boolean withStatistics;
    private String output;

    private Integer hashCode;
    private String asString;

    private FizzBuzz(int from, int to, List<OverrideRule> overrideRules, boolean withStatistics) {
        checkArgument(allPositive(from, to), "Inputs must be positive - from(%s) to(%s)", from, to);
        checkArgument(inAscendingOrder(from, to), "from(%s) cannot be bigger than to(%s)", from, to);
        this.from = from;
        this.to = to;
        this.overrideRules = overrideRules;
        this.withStatistics = withStatistics;
    }

    /**
     * Generates FizzBuzz output based on the input creational
     * parameters which include the numeric range to iterate over
     * as well as any optional override rules and statistics.
     * The immutable output will be cached after first call to
     * optimise performance on subsequent calls.
     * @return FizzBuzz output string.
     */
    public String output() {
        if (output == null) {
            output = IntStream.rangeClosed(from, to)
                    .mapToObj(this::convert)
                    .collect(joining(SPACE));
            if (withStatistics) output += statistics();
        }
        return output;
    }

    private String convert(int value) {
        if (hasOverrideRules()) {
            for (OverrideRule rule : overrideRules) {
                if (rule.matches(value)) {
                    return rule.result();
                }
            }
        }
        if (DIVISIBLE_BY_3_AND_5.test(value)) return FIZZBUZZ;
        if (DIVISIBLE_BY_3.test(value)) return FIZZ;
        if (DIVISIBLE_BY_5.test(value)) return BUZZ;
        return String.valueOf(value);
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

    private String statistics() {
        Map<String, Long> frequencies = calculateFrequencies();
        return formatStatistics(frequencies);
    }

    private Map<String, Long> calculateFrequencies() {
        return IntStream.rangeClosed(from, to)
                .mapToObj(this::convert)
                .collect(groupingBy(fizzBuzzIdentity(), counting()));
    }

    private String formatStatistics(Map<String, Long> frequencies) {
        return LINE_SEPARATOR +
                frequencies.entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue())
                        .sorted()
                        .collect(joining(LINE_SEPARATOR));
    }

    private Function<String, String> fizzBuzzIdentity() {
        return (String s) -> {
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return s;
            }
            return NUMBER;
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FizzBuzz)) return false;
        FizzBuzz that = (FizzBuzz) obj;
        return (this.from == that.from)
                && (this.to == that.to)
                && equal(this.overrideRules, that.overrideRules);
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hashCode(from, to, overrideRules);
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (asString == null) {
            asString = toStringHelper(getClass())
                    .add("from", from)
                    .add("to", to)
                    .add("overrideRules", overrideRules)
                    .toString();
        }
        return asString;
    }

    /**
     * Builder to create immutable FizzBuzz instances.
     */
    public static class Builder {

        private int from = 1;
        private int to = 20;
        private List<OverrideRule> rules = new ArrayList<>();
        private boolean withStatistics = false;

        public Builder() {}

        /**
         * The start value to generate FizzBuzz from.
         * @param from The start value
         * @return FizzBuzz Builder
         */
        public Builder from(int from) {
            this.from = from;
            return this;
        }

        /**
         * The end value to generate FizzBuzz to.
         * @param to The end value
         * @return FizzBuzz Builder
         */
        public Builder to(int to) {
            this.to = to;
            return this;
        }

        /**
         * Add override rule that takes precedence over core
         * FizzBuzz rules. The order of adding rules determines
         * the order of precedence in final output with rules
         * added in order of decreasing precedence.
         * @param rule The override rule.
         * @return FizzBuzz Builder
         */
        public Builder withOverrideRule(OverrideRule rule) {
            rules.add(rule);
            return this;
        }

        /**
         * Whether the generated output should
         * include FizzBuzz statistics.
         * @return FizzBuzz Builder
         */
        public Builder withStatistics() {
            this.withStatistics = true;
            return this;
        }

        /**
         * Build immutable FizzBuzz instance.
         * @return Immutable FizzBuzz instance
         */
        public FizzBuzz build() {
            return new FizzBuzz(from, to, rules, withStatistics);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("Welcome to FizzBuzz.");
        int from = getRangeValue(in, "Please enter the number to generate Fizzbuzz from:");
        int to = getRangeValue(in, "Please enter the number to generate Fizzbuzz to:");
        System.out.println();
        System.out.println("Would you like to add the LUCK override rule to the core game? (Y/n)");
        boolean withLuckRule = in.nextLine().equalsIgnoreCase("Y") ? true : false;
        System.out.println();
        System.out.println("Would you like to add FizzBuzz statistics to the output? (Y/n)");
        boolean withStatistics = in.nextLine().equalsIgnoreCase("Y") ? true : false;

        FizzBuzz.Builder builder = new Builder().from(from).to(to);
        builder = withLuckRule ? builder.withOverrideRule(Rules.LUCK_RULE) : builder;
        builder = withStatistics ? builder.withStatistics() : builder;

        FizzBuzz fizzBuzz = builder.build();

        System.out.println();
        System.out.println("FizzBuzz:");
        System.out.println("---------");
        System.out.println();
        System.out.println(fizzBuzz.output());
    }

    private static int getRangeValue(Scanner in, String message) {
        boolean quit = false;
        String input = null;
        int rangeValue = 0;
        while (!quit) {
            try {
                System.out.println();
                System.out.println(message);
                input = in.nextLine();
                rangeValue = Integer.parseInt(input);
                if (rangeValue < 0) throw new IllegalArgumentException("Invalid input value");
                quit = true;
            } catch (IllegalArgumentException iae) {
                System.out.println(format("'%s' is not a positive integer", input));
            }
        }
        return rangeValue;
    }


}

