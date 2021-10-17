package se.su.it.ladok

class L3UtbildningsTillfallePeriod {
    Date dateCreated
    Edu edu
    Date forstaUndervisningsDatum
    Date lastUpdated
    double omfattningsVarde = 0.0
    L3Period period
    Date sistaUndervisningsDatum
    String uid
    L3UtbildningsTillfalle utbildningsTillfalle

    static constraints = {
        dateCreated(nullable: true)
        edu(nullable: false)
        forstaUndervisningsDatum(nullable: false)
        lastUpdated(nullable: true)
        period(nullable: false)
        sistaUndervisningsDatum(nullable: false)
        uid(nullable: false, blank: false, unique: true)
        utbildningsTillfalle(nullable: false)
    }
}