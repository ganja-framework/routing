package ganja.component.routing.utils

import ganja.component.routing.Route
import spock.lang.Specification
import java.util.regex.PatternSyntaxException
import spock.lang.Unroll

class RouteCompilerSpec extends Specification {

    @Unroll("Path: #path")
    void "it can compile route"() {

        given:
        def subject = new RouteCompiler(pattern: new Pattern())
        def route = new Route(path: path, defaults: defaults )

        when:

        subject.compile(route)

        then:
        route.pattern.pattern() == expected

        where:
        path                                   | defaults          | expected
        '/{foo}'                               | [:]               | '/(?<foo>[^/]+)'
        '/{foo}/{bar}'                         | [:]               | '/(?<foo>[^/]+)/(?<bar>[^/]+)'
        '/{foo}'                               | [foo: 'provided'] | '/(?:/(?<foo>[^/]+))?'
        '/{foo}/{bar}'                         | [bar: 'provided'] | '/(?<foo>[^/]+)/(?:/(?<bar>[^/]+))?'
        '/foo/{foo}/{barbaz}'                  | [:]               | '/foo/(?<foo>[^/]+)/(?<barbaz>[^/]+)'
        '/foo/{foo}/{bar}/edit'                | [:]               | '/foo/(?<foo>[^/]+)/(?<bar>[^/]+)/edit'

        /*
            @todo implement optional groups
            @todo implement static parts of path

            def path = '/page/{foo}/{id}/{ok}'
            def matcher = path =~ /\{\w+\}/
            //matcher.each { println it[1..-2] }

            path = '/foo/some%20var/edit'

            def m = path =~ /\/foo\/(?<param>[^\/]++)\/(?<action>[^\/]++)/
            m.find()
            println m.group('param')
            println m.group('action')

            prints:
            some%20var
            edit

            path = '/foo/some%20var/edit'

            def m = path =~ /\/foo\/(?<param>[^\/]++)(?::\/(?<action>[^\/]++))?/
            m.find()
            println m.group('param')
            println m.group('action')

            prints:
            some%20var
            null
         */
    }

    void "it throws exception if route contains variable more than once"() {

        given:
        def subject = new RouteCompiler(pattern: new Pattern())

        when:
        subject.compile(new Route(path: '/bar/{foo}/show/{foo}'))

        then:
        thrown(IllegalArgumentException)
    }

    void "it throws exception when numeric variable is specified"() {

        given:
        def subject = new RouteCompiler(pattern: new Pattern())

        when:
        subject.compile(new Route(path: '/bar/{1234}'))

        then:
        thrown(PatternSyntaxException)
    }
}
