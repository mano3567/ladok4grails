package se.su.it.ladok

class L3UtbildningsTillfalle {
    Date dateCreated
    Edu edu
    int finansieringsFormId = -1
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
    int undervisningsTidId = -1
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

    L3FinansieringsForm getFinansieringsForm() {
        return L3FinansieringsForm.findByEduAndLadokId(edu, finansieringsFormId)
    }

    L3Organisation getOrganisation() {
        return L3Organisation.findByEduAndUid(edu, organisationUid)
    }

    L3Period getStartPeriod() {
        return L3Period.findByEduAndLadokId(edu, startPeriodId)
    }

    L3StudieLokalisering gertStudieLokalisering() {
        return L3StudieLokalisering.findByEduAndLadokId(edu, studieLokaliseringId)
    }

    L3StudieTakt getSTudieTakt() {
        return L3StudieTakt.findByEduAndLadokId(edu, studieTaktId)
    }

    L3UndervisningsTid getUndervisningsTid() {
        return L3UndervisningsTid.findByEduAndLadokId(edu, undervisningsTidId)
    }

    L3Utbildning getUtbildning() {
        return L3Utbildning.findByEduAndUid(edu, utbildningsInstansUid)
    }

    List<L3UtbildningsTillfallePeriod> getUtbildningsTillfallePerioder() {
        return L3UtbildningsTillfallePeriod.findAllByUtbildningsTillfalle(this, [sort: 'forstaUndervisningsDatum'])
    }

    L3UtbildningsTyp getUtbildningsTyp() {
        return utbildningsTypId>0 ? L3UtbildningsTyp.findByLadokIdAndEdu(utbildningsTypId, edu) : null
    }
}