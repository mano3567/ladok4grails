package se.su.it.ladok

class L3UtbildningsTillfalle {
    Date dateCreated
    Edu edu
    boolean installt = false
    Date lastUpdated
    double omfattningsVarde = 0.0
    String organisationUid
    Date slutDatum
    Date startDatum
    int startPeriodId = -1 // StartperiodID
    int status = -1 // Status
    int studieLokaliseringId = -1 // StudielokaliseringID
    int studieTaktId = -1 // StudietaktID
    String uid
    int undervisningsFormId = -1 // UndervisningsformID
    boolean utannonserat = false
    String utbildningsInstansUid // UtbildningsinstansUID
    String utbildningsMallUid // UtbildningsmallUID
    String utbildningsTillfallesKod
    int utbildningsTypId = -1 // UtbildningstypID

    static constraints = {
        dateCreated(nullable: true)
        edu(nullable: false)
        lastUpdated(nullable: true)
        organisationUid(nullable: false, blank: false)
        slutDatum(nullable: true)
        startDatum(nullable: true)
        uid(nullable: false, blank: false, unique: true)
        utbildningsInstansUid(nullable: false, blank: false)
        utbildningsMallUid(nullable: true)
        utbildningsTillfallesKod(nullable: false, blank: false)
    }
}