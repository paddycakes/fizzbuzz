package com.agilesphere.rules;

/**
 * An interface to represent rules that can be added to FizzBuzz
 * as overrides that take precedence over the core game rules.
 */
public interface OverrideRule {

    /**
     * Determines if value matches the rule.
     * @param value to be matched.
     * @return true if the input value matches the rule, otherwise false
     */
    boolean matches(int value);

    /**
     * Result text that is returned if rule is matched.
     * @return the rule result text.
     */
    String result();

}
