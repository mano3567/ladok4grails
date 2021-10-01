package se.su.it.ladok

import grails.core.GrailsApplication
import grails.gorm.transactions.NotTransactional
import grails.gorm.transactions.Transactional

@Transactional
class SettingsService {
    GrailsApplication grailsApplication

    @Transactional(readOnly = true)
    String getLadok3UrlForEdu(Edu edu) {
        String url = null
        if(edu) {
            ConfigValue configValue = ConfigValue.findByName("ladok3.url.for.${edu.toString()}")
            url = configValue?.value
        }
        return url
    }

    @NotTransactional
    String getPathForCertByEdu(Edu edu) {
        String path = null
        if(edu) {
            path = grailsApplication.config.getProperty("ladok3Settings.${edu.toString().toLowerCase()}.certPath") as String
        }
        return path
    }

    @NotTransactional
    String getPassWordForCertByEdu(Edu edu) {
        String path = null
        if(edu) {
            path = grailsApplication.config.getProperty("ladok3Settings.${edu.toString().toLowerCase()}.certPassWord") as String
        }
        return path
    }

    @Transactional(readOnly = true)
    boolean isLadok3EnabledForEdu(Edu edu) {
        boolean isEnabled = false
        if(edu) {
            ConfigValue configValue = ConfigValue.findByName("ladok3.enabled.for.${edu.toString()}")
            if(configValue?.value) {
                try {
                    isEnabled = Boolean.parseBoolean(configValue.value)
                } catch(Throwable exception) {
                    isEnabled = false
                }
            }
        }
        return isEnabled
    }

    @Transactional
    void setLadok3EnabledForEdu(Edu edu, boolean enabled) {
        if(edu) {
            ConfigValue configValue = ConfigValue.findOrCreateByName("ladok3.enabled.for.${edu.toString()}")
            configValue.value = enabled.toString()
            configValue.save(failOnError: true)
        }
    }

    @Transactional
    void setLadok3UrlForEdu(Edu edu, String url) {
        if(edu) {
            ConfigValue configValue = ConfigValue.findOrCreateByName("ladok3.url.for.${edu.toString()}")
            configValue.value = url?.trim()
            configValue.save(failOnError: true)
        }
    }

    @Transactional
    void updateValue(long id, String someValue) {
        ConfigValue configValue = ConfigValue.get(id)
        if(configValue) {
            if(configValue.valueType==ConfigValueType.BOOLEAN) {
                boolean newValue = false
                if(someValue?.trim()?.length()>0) {
                    try {
                        newValue = Boolean.parseBoolean(someValue.trim())
                    } catch(Throwable exception) {
                        newValue = false
                    }
                }
                configValue.value = newValue.toString()
            } else if(configValue.valueType==ConfigValueType.DATEYYYYMMDD) {
                Date newValue = null
                if(someValue?.trim()?.length()>0) {
                    try {
                        newValue = Date.parse("yyyy-MM-dd", someValue.trim())
                    } catch(Throwable exception) {
                        newValue = null
                    }
                }
                configValue.value = newValue?.format("yyyy-MM-dd")
            } else if(configValue.valueType==ConfigValueType.DATEYYYYMMDDHHMMSS) {
                Date newValue = null
                if(someValue?.trim()?.length()>0) {
                    try {
                        newValue = Date.parse("yyyy-MM-dd HH:mm:ss", someValue.trim())
                    } catch(Throwable exception) {
                        newValue = null
                    }
                }
                configValue.value = newValue?.format("yyyy-MM-dd HH:mm:ss")
            } else if(configValue.valueType==ConfigValueType.DECIMAL) {
                double newValue = 0.0
                if(someValue?.trim()?.length()>0) {
                    try {
                        newValue = Double.parseDouble(someValue.trim())
                    } catch(Throwable exception) {
                        newValue = 0.0
                    }
                }
                configValue.value = newValue.toString()
            } else if(configValue.valueType==ConfigValueType.INTEGER) {
                long newValue = 0L
                if(someValue?.trim()?.length()>0) {
                    try {
                        newValue = Long.parseLong(someValue.trim())
                    } catch(Throwable exception) {
                        newValue = 0L
                    }
                }
                configValue.value = newValue.toString()
            } else if(configValue.valueType==ConfigValueType.STRING) {
                configValue.value = someValue?.trim()
            } else {
                configValue.valueType=ConfigValueType.STRING
                configValue.value = someValue?.trim()
            }
            configValue.save(failOnError: true)
        }
    }
}
