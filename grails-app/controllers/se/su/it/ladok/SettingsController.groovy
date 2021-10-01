package se.su.it.ladok

class SettingsController {
    final static String defaultAction = "list"

    Ladok3Service ladok3Service
    SettingsService settingsService

    def list() {
        ladok3Service.getUncachedUppfoljningFeedRecentId(Edu.SH)
        [configValues: ConfigValue.findAll([sort: 'name', order: 'asc'])]
    }

    def updateValue() {
        if(!params.long('id') || ConfigValue.countById(params.long('id'))<1) {
            response.status = 400
            return render(text: 'Missing ConfigValue')
        }
        settingsService.updateValue(params.long('id'), params.newValue?.trim() as String)
        return render(template: 'updated', model: [someDate: ConfigValue.get(params.long('id')).lastUpdated])
    }
}
