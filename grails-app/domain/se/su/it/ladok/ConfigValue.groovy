package se.su.it.ladok

class ConfigValue {
    Date dateCreated
    Date lastUpdated
    String name
    String value
    ConfigValueType valueType

    static constraints = {
        dateCreated(nullable: true)
        lastUpdated(nullable: true)
        name(nullable: false, blank: false, unique: true)
        value(nullable: true)
        valueType(nullable: false)
    }
}
