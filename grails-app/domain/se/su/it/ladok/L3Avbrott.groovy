package se.su.it.ladok

class L3Avbrott {
    String anteckning
    Date avbrottsDatum
    Date dateCreated
    Edu edu
    String externReferens
    Date lastUpdated
    String studentUid
    String utbildningUid

    static constraints = {
        anteckning(nullable: true, blank: true)
        avbrottsDatum(nullable: false)
        dateCreated(nullable: true)
        edu(nullable: false)
        externReferens(nullable: true)
        lastUpdated(nullable: true)
        studentUid(nullable: false, blank: false)
        utbildningUid(nullable: false, blank: false, unique: ['studentUid'])
    }

    L3Utbildning getUtbildning() {
        return L3Utbildning.findByEduAndUtbildningUidAndSenasteVersion(edu, utbildningUid, true)
    }

    L3Student getStudent() {
        return L3Student.findByUid(studentUid)
    }
}
