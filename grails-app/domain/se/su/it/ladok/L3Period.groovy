package se.su.it.ladok

class L3Period { // Termin in Ladok2
    String benamningEn // Benamning -> en
    String benamningSv // Benamning -> sv
    Date dateCreated
    Edu edu
    Date fromDatum // FromDatum
    String kod // Kod
    int ladokId = -1 // ID
    int larosateId = -1// LarosateID
    Date lastUpdated
    int periodTypId = -1 // PeriodtypID
    Date tomDatum // TomDatum

    static constraints = {
        benamningEn(nullable: false, blank: false)
        benamningSv(nullable: false, blank: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        fromDatum(nullable: false)
        kod(nullable: false, blank: false, unique: 'edu')
        lastUpdated(nullable: true)
        tomDatum(nullable: false)
    }
}
