package se.su.it.ladok

class Ladok3Controller {
    final static String defaultAction = "list"

    Ladok3Service ladok3Service
    SettingsService settingsService

    def index() {
        List<Edu> edus = Edu.values().sort {it.fullName}
        Map<Edu, Date> expirationDates = [:]
        Map<Edu, Boolean> isDefined = [:]
        Map<Edu, Boolean> isEnabled = [:]
        Map<Edu, LatestFeedMeta> lastFeed = [:]
        edus.each { Edu edu ->
            String passWord = settingsService.getPassWordForCertByEdu(edu)
            String path = settingsService.getPathForCertByEdu(edu)
            if(passWord && path) {
                expirationDates.put(edu, ladok3Service.getExpirationDateForCert(path, passWord))
                isDefined.put(edu, true)
                lastFeed.put(edu, ladok3Service.getUncachedUppfoljningFeedRecentId(edu))
            } else {
                expirationDates.put(edu, null)
                isDefined.put(edu, false)
                lastFeed.put(edu, null)
            }
            isEnabled.put(edu, settingsService.isLadok3EnabledForEdu(edu))
        }
        [edus: edus, expirationDates: expirationDates, isDefined: isDefined, isEnabled: isEnabled, lastFeed: lastFeed]
    }

    def test() {
        log.info "test: ${params}"
//        FeedInitializeJob.triggerNow([:])
        UpdateL3BasicsJob.triggerNow([:])
        return render(text: "Blahonga")
    }
}
