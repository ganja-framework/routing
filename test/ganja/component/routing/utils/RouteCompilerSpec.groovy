package ganja.component.routing.utils

import ganja.component.routing.Route
import spock.lang.Specification

class RouteCompilerSpec extends Specification {

    void "it can compile route"() {

        given:
        def subject = new RouteCompiler(pattern: new Pattern())

        when:
        subject.compile(input)

        then:
        input.pattern.pattern() == expected

        where:
        input                                    | expected
        new Route(path: '/{foo}')                | '(?<foo>[^/]++)'
        new Route(path: '/{foo}/{bar}')          | '(?<foo>[^/]++)/(?<bar>[^/]++)'
//        new Route(path: '/foo/{foo}/{bar}')      | '/foo/(?<foo>[^/]++)/(?<bar>[^/]++)'
//        new Route(path: '/foo/{foo}/{bar}/edit') | '/foo/(?<foo>[^/]++)/(?<bar>[^/]++)/edit'

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
}
