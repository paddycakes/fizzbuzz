package com.agilesphere.utils

import spock.lang.Specification

class DigitSpliteratorSpec extends Specification {

    static final int BASE_10 = 10
    static final int BASE_2 = 2

    def "should estimate size by number of digits in base 10 number"() {
        when:
        def spliterator = new DigitSpliterator(number, BASE_10)

        then:
        spliterator.estimateSize() == size

        where:
        number || size
        1      || 1
        12     || 2
        123    || 3
        9789   || 4
        19805  || 5
        0      || 1
    }

    def "should estimate size by number of digits in base 2 number"() {
        when:
        def spliterator = new DigitSpliterator(number, BASE_2)

        then:
        spliterator.estimateSize() == size

        where:
        number || size
        1      || 1
        10     || 2
        101    || 3
        1110   || 4
        10111  || 5
        0      || 1
    }
}
