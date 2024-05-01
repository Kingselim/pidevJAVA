package com.esprit.controllers;

public class SharedData  {
    private static String variable;

    public static String getVariable() {
        return variable;
    }

    public static void setVariable(String value) {
        variable = value;
    }

}
