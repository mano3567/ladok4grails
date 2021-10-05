package se.su.it.ladok

class L3UndervisningsTid {
    String benamningEn
    String benamningSv
    Date dateCreated
    Edu edu
    String beskrivningEn
    String beskrivningSv
    String kod
    int ladokId =-1
    int larosateId =-1
    Date lastUpdated
    Date slutDatum
    Date startDatum

    static constraints = {
        benamningEn(nullable: false, blank: false)
        benamningSv(nullable: false, blank: false)
        beskrivningEn(nullable: true)
        beskrivningSv(nullable: true)
        dateCreated(nullable: true)
        edu(nullable: false)
        kod(nullable: false, blank: false)
        lastUpdated(nullable: true)
        slutDatum(nullable: true)
        startDatum(nullable: true)
    }
}
