package ganja.component.routing

import ganja.component.routing.exception.MethodNotAllowedException
import ganja.component.routing.exception.ResourceNotFoundException
import ganja.component.routing.utils.Pattern
import ganja.component.routing.utils.RouteCompiler
import spock.lang.Specification
import spock.lang.Unroll

class MatcherSpec extends Specification {

    void "it is initalizable"() {

        given:
        def matcher = new Matcher()

        expect:
        matcher instanceof Matcher
    }

    void "it has routes in collection"() {

        given:
        def matcher = new Matcher(collection: new RouteCollection())

        expect:
        ! matcher.collection.size()

        when:
        matcher.collection.add('route1', new Route(path: '/contact'))

        then:
        matcher.collection.size()
    }

    void "it matches route by path"() {

        setup:
        def matcher = new Matcher(compiler: new RouteCompiler(pattern: new Pattern()), collection: new RouteCollection())
        matcher.collection.add('route1', new Route(path:'/post-only', methods: ['post']))
        matcher.collection.add('route2', new Route(path:'/admin'))
        matcher.collection.add('route3', new Route(path:'/admin/pages', methods: ['put']))
        matcher.collection.add('route4', new Route(path:'/', methods: ['get']))

        expect:
        [ route: 'route2' ] == matcher.match('/admin')
        [ route: 'route3' ] == matcher.match('/admin/pages', 'put')
        [ route: 'route3' ] == matcher.match('//admin/pages', 'put')
        [ route: 'route4' ] == matcher.match('/')

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
        '/non-existent' | 'GET'  | ResourceNotFoundException | "Route for '/non-existent' not found."
    }


    @Unroll("Match: #match")
    void "it matches routes with parameters"() {

        setup:
        def matcher = new Matcher(compiler: new RouteCompiler(pattern: new Pattern()), collection: new RouteCollection())
        matcher.collection.add(route, new Route(path: path, defaults: defaults))

        expect:
        result == matcher.match(match)

        where:
        route | path   | defaults | match  | result
        'foo' | '/foo' | [:]      | '/foo' | [route: 'foo']
        'bar' | '/foo' | [bar: 2] | '/foo' | [route: 'bar', bar: 2]
    }
}
