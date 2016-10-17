package com.agilesphere.rules;

public interface OverrideRule {

    boolean matches(int value);

    String result();

}
