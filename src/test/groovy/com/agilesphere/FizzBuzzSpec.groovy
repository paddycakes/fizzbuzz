package com.agilesphere

import com.agilesphere.rules.Rule
import spock.lang.Specification

class FizzBuzzSpec extends Specification {

    def "should throw IllegalArgumentException when any combination of 'from' or 'to' parameters are zero or negative"() {
        when:
        new FizzBuzz.Builder().from(from).to(to).build()

        then:
        def error = thrown(expectedException)
        error.message == expectedMessage

        where:
        from | to || expectedException | expectedMessage
        -5   | 10 || IllegalArgumentException | 'Inputs must be positive - from(-5) to(10)'
        1    | -3 || IllegalArgumentException | 'Inputs must be positive - from(1) to(-3)'
        -3   | -8 || IllegalArgumentException | 'Inputs must be positive - from(-3) to(-8)'
        0    | 20 || IllegalArgumentException | 'Inputs must be positive - from(0) to(20)'
        1    |  0 || IllegalArgumentException | 'Inputs must be positive - from(1) to(0)'
    }

    def "should throw IllegalArgumentException when constructor is passed a 'from' parameter that is larger than 'to' parameter"() {
        when:
        new FizzBuzz.Builder().from(8).to(7).build()

        then:
        IllegalArgumentException e = thrown()
        e.message == "from(8) cannot be bigger than to(7)"
    }

    def "when two rules both match the one added first should take precedence"() {
        given:
        def rule1 = [
                matches: { i -> i == 5 },
                result: { "rule1" },
                rule: { i -> i == 5 }
        ] as Rule

        def rule2 = [
                matches: { i -> i == 8 },
                result: { "rule2" },
                rule: { i -> i == 8 }
        ] as Rule

        def rule3 = [
                matches: { i -> i == 8 },
                result: { "rule3" },
                rule: { i -> i == 8 }
        ] as Rule

        when:
        def fb = new FizzBuzz.Builder().from(8).to(8)
                .withOverrideRule(rule1)
                .withOverrideRule(rule2)
                .withOverrideRule(rule3)
                .build()

        then:
        fb.output() == "rule2"
    }

}