package com.agilesphere

import spock.lang.Specification

class FizzBuzzSpec extends Specification {

    def "should throw IllegalArgumentException when constructor is passed a 'from' parameter that is a negative number"() {
        when:
        new FizzBuzz.Builder().from(-5).to(20).build()

        then:
        IllegalArgumentException e = thrown()
        e.message == "Inputs cannot be negative - from(-5) to(20)"
    }

}