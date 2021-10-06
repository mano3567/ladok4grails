package se.su.it.ladok

class L3UtbildningsTyp {
    boolean avserTillfalle = false
    String benamningEn
    String benamningSv
    String beskrivningEn
    String beskrivningSv
    Date dateCreated
    Edu edu
    int forvaldOmfattning = -1
    String grundTyp
    boolean harOmfattning = false
    boolean individuell = false
    boolean kanUtannonseras = false
    String kod
    int ladokId = -1
    int larosateId = -1
    Date lastUpdated
    int nivaInomStudieordningId = -1
    boolean sjalvstandig = false
    Date slutDatum
    int sorteringsOrdning = -1
    Date startDatum
    int studieOrdningId = -1
    String tillatnaUtbildningstyperIStruktur
    String tillfalleInomUtbildningstyper
    boolean versionsHanteras = false

    static constraints = {
        benamningEn(nullable: false, blank: false)
        benamningSv(nullable: false, blank: false)
        beskrivningEn(nullable: true)
        beskrivningSv(nullable: true)
        dateCreated(nullable: true)
        edu(nullable: false)
        grundTyp(nullable: false, blank: false)
        kod(nullable: false, blank: false)
        lastUpdated(nullable: true)
        slutDatum(nullable: true)
        startDatum(nullable: true)
        tillatnaUtbildningstyperIStruktur(nullable: true)
        tillfalleInomUtbildningstyper(nullable: true)
    }
}
