package com.agilesphere;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FizzBuzzTests {

    @Test
    public void should_print_number_for_1() {
        // When
        FizzBuzz fb = FizzBuzz.to(1);

        // Then
        assertThat(fb.output(), is("1"));
    }

    @Test
    public void should_print_numbers_for_1_and_2() {
        // When
        FizzBuzz fb = FizzBuzz.of(1, 2);

        // Then
        assertThat(fb.output(), is("1 2"));
    }

    @Test
    public void should_print_fizz_for_numbers_divisible_by_3() {
        // When
        FizzBuzz fb = FizzBuzz.of(1, 3);

        // Then
        assertThat(fb.output(), is("1 2 fizz"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void negative_from_input_should_throw_illegal_argument_exception() {
        FizzBuzz.of(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negative_to_input_should_throw_illegal_argument_exception() {
        FizzBuzz.of(1, -5);
    }

    @Test
    public void two_fizzbuzz_with_same_ranges_should_be_equal() {
        // When
        FizzBuzz first = FizzBuzz.of(5, 18);
        FizzBuzz second = FizzBuzz.of(5, 18);

        // Then
        assertEquals(first, second);
    }

    @Test
    public void two_fizzbuzz_with_different_ranges_should_not_be_equal() {
        // When
        FizzBuzz first = FizzBuzz.of(3, 11);
        FizzBuzz second = FizzBuzz.of(7, 21);

        // Then
        assertNotEquals(first, second);
    }

}

