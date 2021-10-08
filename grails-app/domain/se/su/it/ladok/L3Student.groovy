package se.su.it.ladok

class L3Student {
    boolean avliden = false // Avliden
    Date dateCreated
    String efterNamn // Efternamn
    String externtUid // ExterntUID
    boolean felVidEtableringExternt = false // FelVidEtableringExternt
    String fodelseData // Fodelsedata
    Date folkbokforingsBevakningTillOchMed // FolkbokforingsbevakningTillOchMed
    String forNamn // Fornamn
    int konId = -1 // KonID
    Date lastUpdated
    String personNummer // Personnummer
    String senastAndradAv // SenastAndradAv
    Date senastSparad // SenastSparad
    String uid // Uid

    static constraints = {
        dateCreated(nullable: true)
        efterNamn(nullable: false, blank: false)
        externtUid(nullable: false, blank: false)
        fodelseData(nullable: false, blank: false)
        folkbokforingsBevakningTillOchMed(nullable: true)
        forNamn(nullable: false, blank: false)
        lastUpdated(nullable: true)
        personNummer(nullable: false, blank: false, unique: true)
        senastAndradAv(nullable: false, blank: false)
        senastSparad(nullable: false)
        uid(nullable: false, blank: false, unique: true)
    }

    List<L3StudentAdress> getAddresses() {
        return L3StudentAdress.findAllByStudentUid(uid, [sort: 'postAdressTyp'])
    }

    L3StudentKontaktUppgifter getContactInformation() {
        return L3StudentKontaktUppgifter.findByStudentUid(uid)
    }
}
