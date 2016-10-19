package com.agilesphere;

import com.agilesphere.rules.Rule;
import org.junit.Test;

import java.util.function.IntPredicate;

import static com.agilesphere.FizzBuzz.LINE_SEPARATOR;
import static com.agilesphere.rules.Rules.LUCK_RULE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FizzBuzzTests {

    @Test
    public void should_print_number_for_1() {
        // When
        FizzBuzz fb = new FizzBuzz.Builder().to(1).build();

        // Then
        assertThat(fb.output(), is("1"));
    }

    @Test
    public void should_print_numbers_for_1_and_2() {
        // When
        FizzBuzz fb = new FizzBuzz.Builder().to(2).build();

        // Then
        assertThat(fb.output(), is("1 2"));
    }

    @Test
    public void should_print_fizz_for_numbers_divisible_by_3() {
        // When
        FizzBuzz fb = new FizzBuzz.Builder().to(3).build();

        // Then
        assertThat(fb.output(), is("1 2 fizz"));
    }

    @Test
    public void should_print_buzz_for_numbers_divisible_by_5() {
        // When
        FizzBuzz fb = new FizzBuzz.Builder().to(5).build();

        // Then
        assertThat(fb.output(), is("1 2 fizz 4 buzz"));
    }

    @Test
    public void should_print_fizzbuzz_for_numbers_divisible_by_3_and_5() {
        // When
        FizzBuzz fb = new FizzBuzz.Builder().build();

        // Then
        assertThat(fb.output(), is("1 2 fizz 4 buzz fizz 7 8 fizz buzz 11 fizz 13 14 fizzbuzz 16 17 fizz 19 buzz"));
    }

    @Test
    public void with_override_rule_should_print_luck_for_numbers_that_contain_digit_3() {
        // When
        FizzBuzz fb = new FizzBuzz.Builder().to(23).withOverrideRule(LUCK_RULE).build();

        // Then
        assertThat(fb.output(), is("1 2 luck 4 buzz fizz 7 8 fizz buzz 11 fizz luck 14 fizzbuzz 16 17 fizz 19 buzz fizz 22 luck"));
    }

    @Test
    public void when_two_override_rules_both_match_the_one_added_first_should_take_precedence() {
        // Given
        IntPredicate EQUALS_8 = i -> i == 8;
        Rule rule1 = new Rule() {
            @Override
            public boolean matches(int value) {
                return EQUALS_8.test(value);
            }

            @Override
            public String result() {
                return "rule1";
            }

            @Override
            public IntPredicate rule() {
                return EQUALS_8;
            }
        };

        Rule rule2 = new Rule() {
            @Override
            public boolean matches(int value) {
                return EQUALS_8.test(value);
            }

            @Override
            public String result() {
                return "rule2";
            }

            @Override
            public IntPredicate rule() {
                return EQUALS_8;
            }
        };

        // When
        FizzBuzz fb = new FizzBuzz.Builder().from(8).to(8)
                .withOverrideRule(rule1)
                .withOverrideRule(rule2)
                .build();

        // Then
        assertThat(fb.output(), is("rule1"));
    }

    @Test
    public void should_return_statistics_in_fizzbuzz_output_when_flag_set() {
        // When
        FizzBuzz fb = new FizzBuzz.Builder().withOverrideRule(LUCK_RULE).withStatistics().build();

        // Then
        assertThat(fb.output(), is(
                "1 2 luck 4 buzz fizz 7 8 fizz buzz 11 fizz luck 14 fizzbuzz 16 17 fizz 19 buzz"
                        + LINE_SEPARATOR
                        + "buzz: 3"
                        + LINE_SEPARATOR
                        + "fizz: 4"
                        + LINE_SEPARATOR
                        + "fizzbuzz: 1"
                        + LINE_SEPARATOR
                        + "luck: 2"
                        + LINE_SEPARATOR
                        + "number: 10"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_when_negative_from_input() {
        new FizzBuzz.Builder().from(-1).to(5).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_when_zero_from_input() {
        new FizzBuzz.Builder().from(0).to(5).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_when_negative_to_input() {
        new FizzBuzz.Builder().from(1).to(-5).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_when_zero_to_input() {
        new FizzBuzz.Builder().from(1).to(0).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_illegal_argument_exception_when_from_is_bigger_than_to_input() {
        new FizzBuzz.Builder().from(20).to(1).build();
    }

    @Test
    public void two_fizzbuzz_with_same_constructor_params_should_be_equal() {
        // When
        FizzBuzz first = new FizzBuzz.Builder().from(5).to(18).withOverrideRule(LUCK_RULE).withStatistics().build();
        FizzBuzz second = new FizzBuzz.Builder().from(5).to(18).withOverrideRule(LUCK_RULE).withStatistics().build();

        // Then
        assertEquals(first, second);
    }

    @Test
    public void two_fizzbuzz_with_different_ranges_should_not_be_equal() {
        // When
        FizzBuzz first = new FizzBuzz.Builder().from(3).to(11).build();
        FizzBuzz second = new FizzBuzz.Builder().from(7).to(21).build();

        // Then
        assertNotEquals(first, second);
    }

}