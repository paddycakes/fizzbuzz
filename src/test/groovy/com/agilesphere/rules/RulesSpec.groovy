package com.agilesphere.rules

import spock.lang.Specification

class RulesSpec extends Specification {

    def "LUCK_RULE should match any positive number that contains the digit 3"() {
        expect:
        Rules.LUCK_RULE.matches(input) == result

        where:
        input | result
        3     | true
        13    | true
        23    | true
        30    | true
        38    | true
        303   | true
        0     | false
        1     | false
        6     | false
        99    | false
    }

    def "LUCK_RULE should return 'luck' as result"() {
        when:
        def output = Rules.LUCK_RULE.result()

        then:
        output == 'luck'
    }

}
