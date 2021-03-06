package com.agilesphere;

import com.agilesphere.rules.Rule;
import com.google.common.base.Objects;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.agilesphere.rules.Rules.*;
import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

/**
 * FizzBuzz ranges over an ascending sequence of numbers replacing:
 *   - any number divisible by three with the word "fizz"
 *   - any number divisible by five with the word "buzz"
 *   - any number divisible by three and five with the word "fizzbuzz"
 */
public class FizzBuzz {

    private static final IntPredicate IS_NEGATIVE = i -> i <= 0;

    private static final String NUMBER = "number";
    private static final String SPACE = " ";
    static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private final int from;
    private final int to;
    private final List<Rule> coreRules;
    private final List<Rule> overrideRules;
    private final boolean withStatistics;
    private String output;

    private Integer hashCode;
    private String asString;

    private FizzBuzz(int from, int to, List<Rule> overrideRules, boolean withStatistics) {
        checkArgument(allPositive(from, to), "Inputs must be positive - from(%s) to(%s)", from, to);
        checkArgument(inAscendingOrder(from, to), "from(%s) cannot be bigger than to(%s)", from, to);
        this.from = from;
        this.to = to;
        this.coreRules = asList(FIZZBUZZ_RULE, FIZZ_RULE, BUZZ_RULE);
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
        Optional<String> anyOverrideMatch = anyMatchingOverrideRule(value);
        if (anyOverrideMatch.isPresent()) return anyOverrideMatch.get();

        Optional<String> anyCoreMatch = anyMatchingCoreRule(value);
        if (anyCoreMatch.isPresent()) return anyCoreMatch.get();

        return number(value);
    }

    private Optional<String> anyMatchingCoreRule(int value) {
        return anyMatchingRule(value, coreRules);
    }

    private Optional<String> anyMatchingOverrideRule(int value) {
        if (hasOverrideRules()) {
            return anyMatchingRule(value, overrideRules);
        }
        return Optional.empty();
    }

    private Optional<String> anyMatchingRule(int value, List<Rule> rules) {
        String result = null;
        for (Rule rule : rules) {
            if (rule.matches(value)) {
                result = rule.result();
                break;
            }
        }
        return (result != null) ? Optional.of(result) : Optional.empty();
    }

    private String number(int value) {
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
                && equal(this.coreRules, that.coreRules)
                && equal(this.overrideRules, that.overrideRules)
                && (withStatistics == withStatistics);
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hashCode(from, to, coreRules, overrideRules, withStatistics);
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (asString == null) {
            asString = toStringHelper(getClass())
                    .add("from", from)
                    .add("to", to)
                    .add("coreRules", coreRules)
                    .add("overrideRules", overrideRules)
                    .add("withStatistics", withStatistics)
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
        private List<Rule> rules = new ArrayList<>();
        private boolean withStatistics = false;

        public Builder() {}

        /**
         * The start value to generate FizzBuzz from.
         * Must be positive integer.
         * @param from The start value
         * @return FizzBuzz Builder
         */
        public Builder from(int from) {
            this.from = from;
            return this;
        }

        /**
         * The end value to generate FizzBuzz to.
         * Must be positive integer.
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
        public Builder withOverrideRule(Rule rule) {
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
        builder = withLuckRule ? builder.withOverrideRule(LUCK_RULE) : builder;
        builder = withStatistics ? builder.withStatistics() : builder;

        FizzBuzz fizzBuzz = builder.build();

        System.out.println();
        System.out.println("FizzBuzz:");
        System.out.println("---------");
        System.out.println();
        System.out.println(fizzBuzz.output());
        System.out.println();
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
                if (rangeValue <= 0) throw new IllegalArgumentException("Invalid input value");
                quit = true;
            } catch (IllegalArgumentException iae) {
                System.out.println(format("'%s' is not a positive integer", input));
            }
        }
        return rangeValue;
    }
}
