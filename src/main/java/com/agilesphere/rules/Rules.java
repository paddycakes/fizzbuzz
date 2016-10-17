package com.agilesphere.rules;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static com.agilesphere.utils.DigitSpliterator.base10Spliterator;
import static java.util.stream.StreamSupport.intStream;

public class Rules {

    private static final String LUCK = "luck";

    private static final IntPredicate EQUALS_3 = i -> i == 3;

    public static final OverrideRule LUCK_RULE = new OverrideRule() {
        @Override
        public boolean matches(int i) {
            return containsThree(i);
        }

        @Override
        public String result() {
            return LUCK;
        }
    };

    private static boolean containsThree(int i) {
        IntStream digitStream = intStream(base10Spliterator(i), false);
        return digitStream.anyMatch(EQUALS_3);
    }
}
