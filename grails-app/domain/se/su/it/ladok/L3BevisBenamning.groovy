package se.su.it.ladok

class L3BevisBenamning { // Examben in Ladok2
    String anteckning // Anteckning
    String benamningEn // Benamning -> en
    String benamningSv // Benamning -> sv
    Date dateCreated
    Edu edu
    Date giltighetsPeriodSlutDatum // Giltighetsperiod -> Slutdatum
    String kod // Kod
    Date lastUpdated
    double omfattning = 0.0 // Omfattning
    String uid // Uid

    static constraints = {
        anteckning(nullable: true)
        benamningEn(nullable: false, blank: false)
        benamningSv(nullable: false, blank: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        giltighetsPeriodSlutDatum(nullable: true)
        kod(nullable: false, blank: false, unique: 'edu')
        lastUpdated(nullable: true)
        uid(nullable: false, blank: false, unique: true)
    }
}
