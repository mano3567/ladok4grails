package se.su.it.ladok

class L3Utbildning {
    boolean avvecklad = false
    String benamning
    String benamningEn
    String benamningSv
    Date dateCreated
    Edu edu
    int enhetId = -1
    int giltigFranPeriodId = -1
    boolean harInnehall = false
    Date lastUpdated
    double omfattningsVarde = 0.0
    String organisationUid
    String overliggandeUtbildningUid
    int processStatusId = -1
    boolean senasteVersion = false
    int studieOrdningId = -1
    String uid
    int utbildningsFormId = -1
    String utbildningsKod
    String utbildningsMallUid
    String utbildningUid
    int utbildningsTypId = -1
    int versionsNummer = -1

    static constraints = {
        benamning(nullable: false, blank: false)
        benamningSv(nullable: false, blank: false)
        benamningEn(nullable: true)
        dateCreated(nullable: true)
        edu(nullable: false)
        lastUpdated(nullable: true)
        organisationUid(nullable: false, blank: false)
        overliggandeUtbildningUid(nullable: true)
        uid(nullable: false, blank: false, unique: true)
        utbildningsKod(nullable: false, blank: false)
        utbildningsMallUid (nullable: false, blank: false)
        utbildningUid(nullable: false, blank: false)
    }

    L3Period getGiltigFranPeriod() {
        return L3Period.findByEduAndLadokId(edu, giltigFranPeriodId)
    }

    L3Organisation getOrganisation() {
        return L3Organisation.findByEduAndUid(edu, organisationUid)
    }

    L3UtbildningsTyp getUtbildningsTyp() {
        return utbildningsTypId>0 ? L3UtbildningsTyp.findByLadokIdAndEdu(utbildningsTypId, edu) : null
    }
}