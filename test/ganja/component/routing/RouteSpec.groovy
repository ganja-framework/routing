package ganja.component.routing

import spock.lang.Specification
import spock.lang.Unroll

import java.util.regex.Pattern

class RouteSpec extends Specification {

    void "it is initalizable"() {

        given:
        def route = new Route()

        expect:
        route instanceof Route
    }

    @Unroll
    void "it converts paths and make them valid"() {

        given:
        def route = new Route(path: "/{foo}")

        expect:
        route.path == "/{foo}"
        new Route().path == '/'

        when: "setPath() sets the path"
        route.path = input

        then:
        route.path == output

        where:
        input                  | output
        '/{bar}'               | '/{bar}'
        ''                     | '/'
        'bar'                  | '/bar'
        '//path'               | '/path'
        '/admin/user/56/edit'  | '/admin/user/56/edit'
        'admin/user/56/update' | '/admin/user/56/update'
    }

    void "it can have methods"() {

        given:
        def route = new Route()

        expect:
        route.methods == []

        when: "add some methods"
        route.methods = input

        then: "methods are there"
        route.methods.contains(test.toUpperCase()) == result

        where:
        input                   | test      | result
        ['GET']                 | 'GET'     | true
        ['GET']                 | 'get'     | true
        ['GET']                 | 'HEAD'    | true
        ['GET', 'POST']         | 'POST'    | true
        ['GET', 'POST']         | 'HEAD'    | true
        ['GET', 'POST']         | 'GET'     | true
        ['PUT', 'POST']         | 'GET'     | false
        ['PUT', 'POST']         | 'HEAD'    | false
        ['PUT', 'POST']         | 'OPTIONS' | false
        ['PUT', 'POST']         | 'PUT'     | true
        ['PUT', 'POST']         | 'POST'    | true
        ['PUT', 'POST', 'HEAD'] | 'HEAD'    | true
        ['PUT', 'POST', 'HEAD'] | 'GET'     | false
    }

    void "it can have regular expression pattern"() {

        given:
        def route = new Route()

        when:
        route.pattern = ~/\w+/

        then:
        route.pattern instanceof Pattern
    }
}
