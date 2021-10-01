package se.su.it.ladok

import groovy.transform.CompileStatic

@CompileStatic
enum ConfigValueType {
    BOOLEAN("Boolean", "Values that are binary, true or false, yes or no"),
    INTEGER("Integer", "Integer values"),
    DECIMAL("Floating point", "Floating point values"),
    DATEYYYYMMDD("Date", "Some date where hours/minutes is not relevant"),
    DATEYYYYMMDDHHMMSS("Date and time", "Some date where hours/minutes is relevant"),
    STRING("String", "Some random data as a String")

    private final String itsDescription
    private final String itsName

    ConfigValueType(String someName, String someDescription) {
        itsDescription = (someDescription && someDescription.trim().length()>0) ? someDescription.trim() : null
        itsName = (someName && someName.trim().length()>0) ? someName.trim() : null
    }

    String getDescription() {
        return itsDescription
    }

    String getName() {
        return itsName
    }

}