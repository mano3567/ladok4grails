package se.su.it.ladok

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class Ladok3ControllerSpec extends Specification implements ControllerUnitTest<Ladok3Controller> {

    def setup() {
        controller.ladok3Service = Mock(Ladok3Service)
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
