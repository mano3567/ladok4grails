package se.su.it.ladok

class Ladok3Controller {
    final static String defaultAction = "index"

    Ladok3Service ladok3Service
    SettingsService settingsService

    def index() {
        List<Edu> edus = Edu.values().sort {it.fullName}
        Map<Edu, Date> expirationDates = [:]
        Map<Edu, Boolean> isDefined = [:]
        Map<Edu, Boolean> isEnabled = [:]
        edus.each { Edu edu ->
            String passWord = settingsService.getPassWordForCertByEdu(edu)
            String path = settingsService.getPathForCertByEdu(edu)
            if(passWord && path) {
                expirationDates.put(edu, ladok3Service.getExpirationDateForCert(path, passWord))
                isDefined.put(edu, true)
            } else {
                expirationDates.put(edu, null)
                isDefined.put(edu, false)
            }
            isEnabled.put(edu, settingsService.isLadok3EnabledForEdu(edu))
        }
        [edus: edus, expirationDates: expirationDates, isDefined: isDefined, isEnabled: isEnabled]
    }

    def listBevisBenamning() {
        [items: L3BevisBenamning.findAll([sort: ['edu': 'asc', 'kod': 'asc']])]
    }

    def listFinandieringsForm() {
        [items: L3FinansieringsForm.findAll([sort: ['edu': 'asc', 'kod': 'asc']])]
    }

    def listOrganization() {
        [items: L3Organisation.findAll([sort: ['edu': 'asc', 'organisationsKod': 'asc']])]
    }

    def listPeriod() {
        [items: L3Period.findAll([sort: ['edu': 'asc', 'kod': 'asc']])]
    }

    def listStudieLokalisering() {
        [items: L3StudieLokalisering.findAll([sort: ['edu': 'asc', 'kod': 'asc']])]
    }

    def listStudieTakt() {
        [items: L3StudieTakt.findAll([sort: ['edu': 'asc', 'kod': 'asc']])]
    }

    def listUndervisningsForm() {
        [items: L3UndervisningsForm.findAll([sort: ['edu': 'asc', 'kod': 'asc']])]
    }

    def listUndervisningsTid() {
        [items: L3UndervisningsTid.findAll([sort: ['edu': 'asc', 'kod': 'asc']])]
    }

    def listUtbildning() {
        Edu edu = params.edu ? Edu.findByName(params.edu as String) : null
        List<Edu> edus = Edu.values().sort {it.fullName}
        List<L3Utbildning> educations = []
        int educationType = params.int('educationType') ?: 1
        List<Map> educationTypes = [ [id: 1, name: 'Oavsett'], [id: 2, name: 'Program'], [id: 3, name: 'Inriktning'], [id: 4, name: 'Kurs'], [id: 5, name: 'Kurspaketering'] ]
        int latestVersion = params.int('latestVersion') ?: 1
        List<Map> latestVersions = [ [id: 1, name: 'Ja'], [id: 2, name: 'Nej'], [id: 3, name: 'Oavsett'] ]
        String searchFor = params.searchFor?.trim() as String
        if(params.find) {
            educations = ladok3Service.findEducations(edu, educationType, latestVersion, searchFor)
        }
        [edu: edu, educations: educations, educationType: educationType, educationTypes: educationTypes, edus: edus, latestVersion: latestVersion, latestVersions: latestVersions, searchFor: searchFor]
    }

    def listUtbildningsTyp() {
        [items: L3UtbildningsTyp.findAll([sort: ['edu': 'asc', 'kod': 'asc']])]
    }

    def showUtbildning() {
        L3Utbildning education = params.long('id') ? L3Utbildning.get(params.long('id')) : null
        if(!education) {
            log.info "Missing education"
        }
        [education: education]
    }

    def test() {
//        FeedInitializeJob.triggerNow([:])
//        UpdateL3BasicsJob.triggerNow([:])
//        UpdateL3Program4EduJob.triggerNow([edu: Edu.SU.name])
        UpdateL3Kurs4EduJob.triggerNow(edu: Edu.KF.name)
        return render(text: "Blahonga")
    }
}
