package com.agilesphere

import com.agilesphere.rules.Rules.LUCK_RULE
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class FizzBuzzScalaSpec extends WordSpec with Matchers {

  def withFizzBuzzBuilder(test: FizzBuzz.Builder => Unit): Unit = {
    val builder = new FizzBuzz.Builder()
    test(builder)
  }

  "Calling FizzBuzz using only core rules" should {

    "output range using only core rules" in {
      withFizzBuzzBuilder {
        _.build().output() shouldBe "1 2 fizz 4 buzz fizz 7 8 fizz buzz 11 fizz 13 14 fizzbuzz 16 17 fizz 19 buzz"
      }
    }

    "output statistics using only core rules" in {
      withFizzBuzzBuilder {
        _.withStatistics().build().output() shouldBe
          """1 2 fizz 4 buzz fizz 7 8 fizz buzz 11 fizz 13 14 fizzbuzz 16 17 fizz 19 buzz
            |buzz: 3
            |fizz: 5
            |fizzbuzz: 1
            |number: 11""".stripMargin
      }
    }

  }

  "Calling FizzBuzz using an override rule in addition to core rules" should {

    "output range to include override rule" in {
      withFizzBuzzBuilder {
        _.withOverrideRule(LUCK_RULE).build().output() shouldBe "1 2 luck 4 buzz fizz 7 8 fizz buzz 11 fizz luck 14 fizzbuzz 16 17 fizz 19 buzz"
      }
    }

    "output statistics using both override rule and core rules" in {
      withFizzBuzzBuilder {
        _.withOverrideRule(LUCK_RULE).withStatistics().build().output() shouldBe
          """1 2 luck 4 buzz fizz 7 8 fizz buzz 11 fizz luck 14 fizzbuzz 16 17 fizz 19 buzz
            |buzz: 3
            |fizz: 4
            |fizzbuzz: 1
            |luck: 2
            |number: 10""".stripMargin
      }
    }

  }

  "Calling FizzBuzz builder" should {

    "throw IllegalArgumentException when 'from' value is negative" in {
      intercept[IllegalArgumentException] {
        withFizzBuzzBuilder {
          _.from(-5).build()
        }
      }
    }

    "throw IllegalArgumentException when 'from' value is zero" in {
      intercept[IllegalArgumentException] {
        withFizzBuzzBuilder {
          _.from(0).build()
        }
      }
    }

    "throw IllegalArgumentException when 'to' value is negative" in {
      intercept[IllegalArgumentException] {
        withFizzBuzzBuilder {
          _.to(-9).build()
        }
      }
    }

    "throw IllegalArgumentException when 'to' value is zero" in {
      intercept[IllegalArgumentException] {
        withFizzBuzzBuilder {
          _.to(0).build()
        }
      }
    }

    "throw IllegalArgumentException when 'from' value is bigger than 'to' value" in {
      intercept[IllegalArgumentException] {
        withFizzBuzzBuilder {
          _.from(12).to(10).build()
        }
      }
    }

  }

}
