package se.su.it.ladok

class AtomFeedHistory {
    Date dateCreated
    Edu edu
    String event
    String eventType
    String eventUid
    int feedId = -1
    Date lastUpdated

    static constraints = {
        dateCreated(nullable: true)
        edu(nullable: false)
        event(nullable: false, blank: false)
        eventType(nullable: false, blank: false)
        eventUid(nullable: false, blank: false, unique: 'edu')
        lastUpdated(nullable: true)
    }
}
