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


    /**
     * Core rule that will match any value that is
     * divisible by 3 and will return the string 'fizz'.
     */
    public static final Rule FIZZ_RULE = new Rule() {

        final IntPredicate DIVISIBLE_BY_3 = i -> i % 3 == 0;

        @Override
        public boolean matches(int value) {
            return DIVISIBLE_BY_3.test(value);
        }

        @Override
        public String result() {
            return FIZZ;
        }

        @Override
        public IntPredicate rule() {
            return DIVISIBLE_BY_3;
        }
    };

    /**
     * Core rule that will match any value that is
     * divisible by 5 and will return the string 'buzz'.
     */
    public static final Rule BUZZ_RULE = new Rule() {

        final IntPredicate DIVISIBLE_BY_5 = i -> i % 5 == 0;

        @Override
        public boolean matches(int value) {
            return DIVISIBLE_BY_5.test(value);
        }

        @Override
        public String result() {
            return BUZZ;
        }

        @Override
        public IntPredicate rule() {
            return DIVISIBLE_BY_5;
        }
    };

    /**
     * Core rule that will match any value that is divisible
     * by both 3 and 5 and will return the string 'fizzbuzz'.
     */
    public static final Rule FIZZBUZZ_RULE = new Rule() {

        final IntPredicate DIVISIBLE_BY_3_AND_5 = FIZZ_RULE.rule().and(BUZZ_RULE.rule());

        @Override
        public boolean matches(int value) {
            return DIVISIBLE_BY_3_AND_5.test(value);
        }

        @Override
        public String result() {
            return FIZZBUZZ;
        }

        @Override
        public IntPredicate rule() {
            return DIVISIBLE_BY_3_AND_5;
        }
    };

    /**
     * Override rule that will match any value that contains a
     * three and will return the string 'luck'.
     */
    public static final Rule LUCK_RULE = new Rule() {

        private static final String LUCK = "luck";
        private final IntPredicate EQUALS_3 = i -> i == 3;

        @Override
        public boolean matches(int value) {
            return containsThree(value);
        }

        @Override
        public String result() {
            return LUCK;
        }

        @Override
        public IntPredicate rule() {
            return EQUALS_3;
        }

        private boolean containsThree(int i) {
            IntStream digitStream = intStream(base10Spliterator(i), false);
            return digitStream.anyMatch(EQUALS_3);
        }
    };

}
