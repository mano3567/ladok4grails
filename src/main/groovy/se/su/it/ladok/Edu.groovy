package se.su.it.ladok

import groovy.transform.CompileStatic

@CompileStatic
enum Edu {
    LU('lu.se', 'Lunds universitet', 'lu.se', "lu.se", "LU"),
    MAU('mau.se', 'Malmö universitet', 'mah.se', "mah.se", "MAU"),
    SH('suni.se', 'Södertörns högskola', 'suni.se', "suni.se", "SH"),
    SU('su.se', 'Stockholms universitet', 'su.se',  "su.se", "SU"),

    private final String abbreviation
    private final String fullName
    private final String name
    private final String scope
    private final String studentScope

    Edu(String name,  String fullName, String scope, String studentScope, String abbreviation) {
        this.name = name
        this.fullName = fullName
        this.scope = scope
        this.studentScope = studentScope
        this.abbreviation = abbreviation
    }

    static Edu findByName(String name) {
        for (e in Edu.values()) {
          if (name == e.name) {
            return e
          }
        }
        return null
    }

    String getAbbreviation() {
        return abbreviation
    }

    String getFullName() {
        return fullName
    }

    String getName() {
        return name
    }

    String getScope() {
        return scope
    }

    String getStudentScope() {
        return studentScope
    }
}

