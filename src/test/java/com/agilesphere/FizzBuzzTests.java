package com.agilesphere;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

}

