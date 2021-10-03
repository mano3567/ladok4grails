package se.su.it.ladok

class FeedInitializeJob {
    static triggers = {
        // no defined trigger, we schedule it manually
    }

    String group = "GRAILS_JOBS"
    String description = "Feed Init Job"

    // not volatile, so persisted on restart.
    boolean volatility = true
    Ladok3Service ladok3Service
    SettingsService settingsService

    void execute(org.quartz.JobExecutionContext context) {
        long start = System.currentTimeMillis()
        log.info "Starting FeedInitializeJob.execute()"
        Map<Edu, Integer> latestFeedId = [:]
        Edu.values().each { Edu edu ->
            if(settingsService.isLadok3EnabledForEdu(edu)) {
                if(settingsService.getPassWordForCertByEdu(edu) && settingsService.getPathForCertByEdu(edu)) {
                    int lastFeedId = ladok3Service.getUncachedUppfoljningFeedRecentId(edu).getParsedFeedId()
                    if(lastFeedId>0) {
                        latestFeedId.put(edu, lastFeedId)
                    }
                    try {
                        Thread.sleep(250L)
                    } catch(Throwable exception) {
                    }
                }
            }
        }
        int counter = -100
        while(counter<1) {
            Edu.values().each {Edu edu ->
                if(latestFeedId.get(edu)) {
                    log.info "trying to update feed ${latestFeedId.get(edu)+counter} for ${edu}"
                    ladok3Service.updateAtomFeedHistoryFromFeed(edu, latestFeedId.get(edu)+counter)
                    try {
                        Thread.sleep(1000L)
                    } catch(Throwable exception) {
                    }
                }
            }
            counter++
        }
        log.info "FeedInitializeJob.execute(): is done in ${System.currentTimeMillis()-start} ms"
    }
}