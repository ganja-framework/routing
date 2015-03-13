package ganja.component.routing

import spock.lang.Specification

class RouteCollectionSpec extends Specification {

    void "it can store routes"() {

        given:
        def collection = new RouteCollection()

        when:
        collection.add('route1', new Route())
        collection.add('route2', new Route())

        then:
        collection.size() == 2
    }

    void "it can add route prefix to all existing routes"() {

        given:
        def collection = new RouteCollection()
        def route1 = new Route(path: '/contact')
        def route2 = new Route(path: '/about')

        when:
        collection.add('route1', route1)
        collection.add('route2', route2)
        collection.setPrefix('/new')

        then:
        route1.path == '/new/contact'
        route2.path == '/new/about'
    }

    void "it can be iterated over"() {

        given:
        def collection = new RouteCollection()
        def route1 = new Route(path: '/contact')
        def route2 = new Route(path: '/about')

        when:
        collection.add('route1', route1)
        collection.add('route2', route2)

        then:
        collection.values().each { assert it instanceof Route }
        collection.each { name, route ->
            assert name instanceof String
            assert route instanceof Route
        }
        for(route in collection.values()) { assert route instanceof Route }
    }
}
