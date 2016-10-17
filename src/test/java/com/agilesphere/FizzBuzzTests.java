package com.agilesphere;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FizzBuzzTests {

    @Test
    public void should_print_number_for_one() {
        // When
        FizzBuzz fb = FizzBuzz.to(1);

        // Then
        assertThat(fb.output(), is("1"));
    }

    @Test
    public void should_print_numbers_for_one_and_two() {
        // When
        FizzBuzz fb = FizzBuzz.of(1, 2);

        // Then
        assertThat(fb.output(), is("1 2"));
    }

}

