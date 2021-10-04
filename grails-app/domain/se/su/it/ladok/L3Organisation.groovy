package se.su.it.ladok

class L3Organisation { // Inst in Ladok2
    String benamningEn // Benamning -> en
    String benamningSv // Benamning -> sv
    Date dateCreated
    Edu edu
    Date giltighetsPeriodSlutDatum // Giltighetsperiod -> Slutdatum
    Date lastUpdated
    int organisationsEnhetsTyp = -1 // Organisationsenhetstyp
    String organisationsKod // Organisationskod
    String uid // Uid

    static constraints = {
        benamningEn(nullable: true)
        benamningSv(nullable: false, blank: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        giltighetsPeriodSlutDatum(nullable: true)
        lastUpdated(nullable: true)
        organisationsKod(nullable: false, blank: false, unique: 'edu')
        uid(nullable: false, blank: false, unique: true)
    }

    boolean isActive() {
        return (null==giltighetsPeriodSlutDatum || giltighetsPeriodSlutDatum.after(Date.newInstance()))
    }
}
