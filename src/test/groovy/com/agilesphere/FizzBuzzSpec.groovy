package com.agilesphere

import spock.lang.Specification

class FizzBuzzSpec extends Specification {

    def "should throw IllegalArgumentException when any combination of 'from' or 'to' parameters are_negative"() {
        when:
        new FizzBuzz.Builder().from(from).to(to).build()

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        from | to || expectedException | expectedMessage
        -5   | 10 || IllegalArgumentException | 'Inputs cannot be negative - from(-5) to(10)'
        1    | -3 || IllegalArgumentException | 'Inputs cannot be negative - from(1) to(-3)'
        -3   | -8 || IllegalArgumentException | 'Inputs cannot be negative - from(-3) to(-8)'
    }

    def "should throw IllegalArgumentException when constructor is passed a 'from' parameter that is larger than 'to' parameter"() {
        when:
        new FizzBuzz.Builder().from(8).to(7).build()

        then:
        IllegalArgumentException e = thrown()
        e.message == "from(8) cannot be bigger than to(7)"
    }

}