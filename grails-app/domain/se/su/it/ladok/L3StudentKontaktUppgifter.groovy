package se.su.it.ladok

class L3StudentKontaktUppgifter {
    Date dateCreated
    String epostAdress // Epostadress
    Date epostAdressSenastAndrad // EpostadressSenastAndrad
    Date lastUpdated
    String senastAndradAv // SenastAndradAv
    Date senastSparad // SenastSparad
    String studentUid // StudentUID
    String telefonNummer // Telefonnummer
    Date telefonNummerSenastAndrad // TelefonnummerSenastAndrad
    String uid // Uid

    static constraints = {
        dateCreated(nullable: true)
        epostAdress(nullable: true)
        epostAdressSenastAndrad(nullable: true)
        lastUpdated(nullable: true)
        senastAndradAv(nullable: false, blank: false)
        senastSparad(nullable: false)
        studentUid(nullable: false, blank: false)
        telefonNummer(nullable: true)
        telefonNummerSenastAndrad(nullable: true)
        uid(nullable: false, blank: false, unique: true)
    }

    L3Student getStudent() {
        return L3Student.findByUid(studentUid)
    }
}
