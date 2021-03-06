package com.agilesphere.rules;

import java.util.function.IntPredicate;

/**
 * An interface to represent rules that can be added to FizzBuzz both as
 * core game rules or as overrides that take precedence over core rules.
 */
public interface Rule {

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

    /**
     * The IntPredicate that defines the rule.
     * @return the rule
     */
    IntPredicate rule();

}
