package ganja.component.routing

import spock.lang.Specification

class RouterSpec extends Specification {

    void "it is initalizable"() {

        given:
        def router = new Router()

        expect:
        router instanceof Router
    }

    void "it generates URLs or paths from route name and parameters"() {

        given:
        def router = new Router()

        expect:
        router.generate('homepage') == '/'
    }
}
