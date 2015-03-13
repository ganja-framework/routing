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