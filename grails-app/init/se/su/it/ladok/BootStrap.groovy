package se.su.it.ladok

class BootStrap {
    def init = { servletContext ->
        Edu.values().each {Edu edu ->
            if(ConfigValue.countByName("ladok3.enabled.for.${edu.toString()}")<1) {
                ConfigValue.withTransaction {
                    ConfigValue.newInstance(name: "ladok3.enabled.for.${edu.toString()}", value: Boolean.FALSE.toString(), valueType: ConfigValueType.BOOLEAN).save(flush: true, failOnError: true)
                }
            }
            if(ConfigValue.countByName("ladok3.url.for.${edu.toString()}")<1) {
                ConfigValue.withTransaction {
                    ConfigValue.newInstance(name: "ladok3.url.for.${edu.toString()}", value: "https://api.ladok.se", valueType: ConfigValueType.STRING).save(flush: true, failOnError: true)
                }
            }
        }
    }

    def destroy = {
    }
}
