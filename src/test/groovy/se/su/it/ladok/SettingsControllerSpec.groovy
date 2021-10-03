package se.su.it.ladok

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class SettingsControllerSpec extends Specification implements ControllerUnitTest<SettingsController> {

    def setup() {
        controller.settingsService = Mock(SettingsService)
    }

    def cleanup() {
    }

    void "test something useful"() {
        given:

        when:
        1+2

        then:
        1<2
    }
}
