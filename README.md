FizzBuzz
--------

## Overview

An application to generate FizzBuzz output based on:
 
- a configurable 'from' and 'to' range
- whether to include the LUCK override rule
- whether to include statistics

I have used Java 8 to implement this and attempted a functional approach where appropriate.

The specification can be found in /docs/Specification.docx

## Gradle Build

The project is built with Gradle:

http://www.gradle.org/

This project includes the Gradle Wrapper (gradlew):

http://www.gradle.org/docs/current/userguide/gradle_wrapper.html

The Gradle Wrapper is a batch script on Windows or a shell script on *nix. When you start a Gradle build via the wrapper, Gradle will be automatically downloaded and used to run the build. This provides the benefit that anyone can work with it without needing to install Gradle beforehand. It also ensures that users are guaranteed to use the version of Gradle that the build was designed to work with.

## Spock for BDD Testing

I have provided a JUnit test suite in the following class:

	com.agilesphere.FizzBuzzTests

However, I have also written some tests with a BDD approach using Spock. Spock is a developer testing and specification framework for Java and Groovy applications. It provides a very powerful and expressive specification language through the power of a Groovy DSL. Spock specifications conventionally have the suffix 'Spec'. One of it's power features, and one which I have used, is the ability it affords for data driven testing. Often it is useful to exercise the same test code multiple times, with varying inputs and expected results. Spock’s data driven testing support makes this extremely easy and I have utilised this in the following specification tests:

    com.agilesphere.FizzBuzzSpec
    com.agilesphere.rules.RulesSpec
    com.agilesphere.utils.DigitSpliteratorSpec

## Build Tasks

### Clean the output directories:

	./gradlew clean

### Running tests

	./gradlew test
	
Test reports can be found at:

	build/reports/tests/index.html

### Running application

Firstly, install the application

	./gradlew installApp
	
Then navigate to

	build/install/fizzbuzz

and execute on Unix

	./bin/fizzbuzz

or on Windows

	./bin/fizzbuzz.bat
	
The program will ask the following questions before generating the FizzBuzz output:

1. What number to generate FizzBuzz from?
2. What number to generate FizzBuzz to?
3. Whether to include the LUCK override rule?
4. Whether to include FizzBuzz statistics in output?
			
### Creating a distribution

Execute this command

	./gradlew distZip
	
and then you will find the distribution created at:

	build/distributions/fizzbuzz.zip