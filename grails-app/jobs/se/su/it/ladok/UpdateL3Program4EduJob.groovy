package se.su.it.ladok

class UpdateL3Program4EduJob {
    static triggers = {
        // no defined trigger, we schedule it manually
    }

    String group = "GRAILS_JOBS"
    String description = "Update L3Programs Job"

    // not volatile, so persisted on restart.
    boolean volatility = true

    Ladok3Service ladok3Service
    SettingsService settingsService

    void execute(org.quartz.JobExecutionContext context) {
        long start = System.currentTimeMillis()
        Edu edu = Edu.findByName(context.mergedJobDataMap.get('edu') as String)

        log.info "Starting UpdateL3Program4EduJob.execute() for ${edu}"
        if(edu) {
            if(settingsService.isLadok3EnabledForEdu(edu)) {
                if(settingsService.getPassWordForCertByEdu(edu) && settingsService.getPathForCertByEdu(edu)) {
                    L3Program.UTBILDNINGSTYPER.each {String utbildningsTyp ->
                        ladok3Service.updateL3UtbildningByEduAndType(edu, utbildningsTyp)
                        try {
                            Thread.sleep(1000L)
                        } catch(Throwable exception) {
                        }
                    }
                    L3ProgramInriktning.UTBILDNINGSTYPER.each {String utbildningsTyp ->
                        ladok3Service.updateL3UtbildningByEduAndType(edu, utbildningsTyp)
                        try {
                            Thread.sleep(1000L)
                        } catch(Throwable exception) {
                        }
                    }
                    L3KursPaketering.UTBILDNINGSTYPER.each {String utbildningsTyp ->
                        ladok3Service.updateL3UtbildningByEduAndType(edu, utbildningsTyp)
                        try {
                            Thread.sleep(1000L)
                        } catch(Throwable exception) {
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
        log.info "UpdateL3Program4EduJob.execute(${edu}): is done in ${System.currentTimeMillis()-start} ms"
    }
}
