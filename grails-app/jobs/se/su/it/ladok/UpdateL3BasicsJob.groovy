package se.su.it.ladok

class UpdateL3BasicsJob {
    static triggers = {
        // no defined trigger, we schedule it manually
    }

    String group = "GRAILS_JOBS"
    String description = "Update L3Basics Job"

    // not volatile, so persisted on restart.
    boolean volatility = true

    Ladok3Service ladok3Service
    SettingsService settingsService

    void execute(org.quartz.JobExecutionContext context) {
        long start = System.currentTimeMillis()
        log.info "Starting UpdateL3BasicsJob.execute()"
        Edu.values().each { Edu edu ->
            if(settingsService.isLadok3EnabledForEdu(edu) && settingsService.getPassWordForCertByEdu(edu) && settingsService.getPathForCertByEdu(edu)) {
                ladok3Service.updateL3BevisBenamning(edu)
                ladok3Service.updateL3FinansieringsForm(edu)
                ladok3Service.updateL3Organizations(edu)
                ladok3Service.updateL3Periods(edu)
                ladok3Service.updateL3StudieLokalisering(edu)
                ladok3Service.updateL3StudieTakt(edu)
                ladok3Service.updateL3UndervisningsForm(edu)
                ladok3Service.updateL3UndervisningsTid(edu)
            }
        }
        log.info "UpdateL3BasicsJob.execute(): is done in ${System.currentTimeMillis()-start} ms"
    }
}
