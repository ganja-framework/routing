package ganja.component.routing

import ganja.component.routing.exception.MethodNotAllowedException
import ganja.component.routing.exception.ResourceNotFoundException
import spock.lang.Specification

class MatcherSpec extends Specification {

    void "it is initalizable"() {

        given:
        def matcher = new Matcher()

        expect:
        matcher instanceof Matcher
    }

    void "it has routes"() {

        given:
        def matcher = new Matcher()

        expect:
        ! matcher.routes

        when:
        matcher.routes['route_name'] = new Route()

        then:
        matcher.routes
    }

    void "it matches route by path"() {

        setup:
        def matcher = new Matcher()
        matcher.routes['route1'] = new Route(path:'/post-only', options: [ controller: 'postController'], methods: ['post'])
        matcher.routes['route2'] = new Route(path:'/admin', options: [ controller: 'adminController'])
        matcher.routes['route3'] = new Route(path:'/admin/pages', options: [ controller: 'pagesController'], methods: ['put'])
        matcher.routes['route4'] = new Route(path:'/', options: [ controller: 'homeController'], methods: ['get'])

        expect:
        [ controller: 'adminController'] == matcher.match('/admin')
        [ controller: 'pagesController'] == matcher.match('/admin/pages', 'put')
        [ controller: 'pagesController'] == matcher.match('//admin/pages', 'put')
        [ controller: 'homeController'] == matcher.match('/')

        when:
        matcher.match(path, method)

        then:
        def e = thrown(exception)
        e.message == message


        where:
        path            | method | exception                 | message
        '/post-only'    | 'PUT'  | MethodNotAllowedException | "Method 'PUT' is not allowed. Allowed methods: [POST]"
        '/post-only'    | 'GET'  | MethodNotAllowedException | "Method 'GET' is not allowed. Allowed methods: [POST]"
        '/post-only'    | 'HEAD' | MethodNotAllowedException | "Method 'HEAD' is not allowed. Allowed methods: [POST]"
        '/admin/pages'  | 'GET'  | MethodNotAllowedException | "Method 'GET' is not allowed. Allowed methods: [PUT]"
        '/admin/pages'  | 'HEAD' | MethodNotAllowedException | "Method 'HEAD' is not allowed. Allowed methods: [PUT]"
        '/non-existent' | 'GET'  | ResourceNotFoundException | null
    }
}
