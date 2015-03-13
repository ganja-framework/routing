package ganja.component.routing.utils

import ganja.component.routing.Route

class RouteCompiler {

    Pattern pattern

    void compile(Route route) {

        String regexp = pattern.extract(route.path).collect({

            "(?<${it}>[^/]++)"

        }).join('/')

        route.pattern = java.util.regex.Pattern.compile(regexp)
    }
}

/*

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