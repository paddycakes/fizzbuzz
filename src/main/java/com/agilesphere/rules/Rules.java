package com.agilesphere.rules;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.agilesphere.utils.DigitSpliterator.base10Spliterator;
import static java.util.stream.StreamSupport.intStream;

/**
 * This class consists exclusively of static members
 * that represent FizzBuzz core rules and override rules.
 */
public class Rules {

    public static final String FIZZ = "fizz";
    public static final String BUZZ = "buzz";
    public static final String FIZZBUZZ = FIZZ + BUZZ;
    public static final String LUCK = "luck";

    private static final IntPredicate DIVISIBLE_BY_3 = i -> i % 3 == 0;
    private static final IntPredicate DIVISIBLE_BY_5 = i -> i % 5 == 0;
    private static final IntPredicate DIVISIBLE_BY_3_AND_5 = DIVISIBLE_BY_3.and(DIVISIBLE_BY_5);

    public static final Rule FIZZ_RULE = new Rule() {

        @Override
        public boolean matches(int value) {
            return DIVISIBLE_BY_3.test(value);
        }

        @Override
        public String result() {
            return FIZZ;
        }
    };

    public static final Rule BUZZ_RULE = new Rule() {

        @Override
        public boolean matches(int value) {
            return DIVISIBLE_BY_5.test(value);
        }

        @Override
        public String result() {
            return BUZZ;
        }
    };

    public static final Rule FIZZBUZZ_RULE = new Rule() {

        @Override
        public boolean matches(int value) {
            return DIVISIBLE_BY_3_AND_5.test(value);
        }

        @Override
        public String result() {
            return FIZZBUZZ;
        }
    };

    /**
     * Override rule that will match any value that contains a
     * three and will return the text 'luck'.
     */
    public static final Rule LUCK_RULE = new Rule() {

        private final IntPredicate EQUALS_3 = i -> i == 3;

        @Override
        public boolean matches(int value) {
            return containsThree(value);
        }

        @Override
        public String result() {
            return LUCK;
        }

        private boolean containsThree(int i) {
            IntStream digitStream = intStream(base10Spliterator(i), false);
            return digitStream.anyMatch(EQUALS_3);
        }
    };


}
