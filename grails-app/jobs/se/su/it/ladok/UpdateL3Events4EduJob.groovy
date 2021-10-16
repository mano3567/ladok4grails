package se.su.it.ladok

class UpdateL3Events4EduJob {
    static triggers = {
        // no defined trigger, we schedule it manually
    }

    String group = "GRAILS_JOBS"
    String description = "Update L3Events Job"

    // not volatile, so persisted on restart.
    boolean volatility = true

    Ladok3Service ladok3Service
    SettingsService settingsService

    void execute(org.quartz.JobExecutionContext context) {
        long start = System.currentTimeMillis()
        Edu edu = Edu.findByName(context.mergedJobDataMap.get('edu') as String)

        log.info "Starting UpdateL3Events4EduJob.execute() for ${edu}"
        if(edu) {
            if(settingsService.isLadok3EnabledForEdu(edu)) {
                if(settingsService.getPassWordForCertByEdu(edu) && settingsService.getPathForCertByEdu(edu)) {
                    ["2007PRG", "2007INR", "2007KP", "2007GKURS", "2007AKURS"].each { String educationType ->
                        L3UtbildningsTyp utbildningsTyp = L3UtbildningsTyp.findByEduAndKod(edu, educationType)
                        if(utbildningsTyp) {
                            int counter = 0
                            L3Utbildning.findAllByUtbildningsTypIdAndEdu(utbildningsTyp.ladokId, edu).each {L3Utbildning education ->
                                if(0==counter%100) {
                                    log.info "Processing events for ${edu} and ${educationType}: ${counter}"
                                }
                                try {
                                    ladok3Service.updateL3UtbildningsEventByEduAndUid(edu, education.uid, educationType)
                                } catch(Throwable exception) {
                                }
                                counter ++
                                try {
                                    Thread.sleep(250L)
                                } catch(Throwable exception) {
                                }
                            }
                        }
                    }
                } else {
                    log.info "${edu} missing path or passwd"
                }
            } else {
                log.info "${edu} is not enabled"
            }
        } else {
            log.info "Missing Edu"
        }
        log.info "UpdateL3Events4EduJob.execute(${edu}): is done in ${System.currentTimeMillis()-start} ms"
    }
}
