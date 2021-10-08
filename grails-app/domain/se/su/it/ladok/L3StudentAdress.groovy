package se.su.it.ladok

class L3StudentAdress {
    transient static final String FOLKBOKFORINGSADRESSTYP = "FOLKBOKFORINGSADRESS"
    transient static final String POSTADRESSTYP = "POSTADRESS"

    String careOf // CareOf
    Date dateCreated
    String land // Land
    Date lastUpdated
    String postAdressTyp // PostadressTyp
    String postNummer // Postnummer
    String postOrt // Postort
    Date senastAndrad // SenastAndrad
    String studentUid
    String utdelningsAdress // Utdelningsadress

    static constraints = {
        careOf(nullable: true)
        dateCreated(nullable: true)
        land(nullable: true)
        lastUpdated (nullable: true)
        postAdressTyp(nullable: false, blank: false)
        postNummer(nullable: false, blank: false)
        postOrt(nullable: false, blank: false)
        senastAndrad (nullable: false)
        studentUid(nullable: false, blank: false, unique: 'postAdressTyp')
        utdelningsAdress(nullable: false, blank: false)
    }

    L3Student getStudent() {
        return L3Student.findByUid(studentUid)
    }
}
