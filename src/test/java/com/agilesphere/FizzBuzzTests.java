package com.agilesphere;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FizzBuzzTests {

    @Test
    public void should_print_number_for_one() {
        // When
        Fizzbuzz fb = Fizzbuzz.to(1);

        // Then
        assertThat(fb.output(), is("1"));
    }
}

