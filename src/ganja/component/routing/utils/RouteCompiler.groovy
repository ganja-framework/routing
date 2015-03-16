package ganja.component.routing.utils

import ganja.component.routing.Route

class RouteCompiler {

    Pattern pattern

    void compile(Route route) {

        List variables = []
        Integer position = 0
        String suffix = ''

        String regexp = pattern.extract(route.path).collect({

            if(variables.contains(it[1])) {
                throw new IllegalArgumentException("Route pattern \"${route.path}\" cannot reference variable name \"${it[1]}\" more than once.")
            }

            variables.add(it[1])

            def prefix = route.path.substring(position, route.path.indexOf(it[0]))

            suffix = route.path.substring(route.path.indexOf(it[0]) + it[0].size())

            position = route.path.indexOf(it[0]) + it[0].size()

            if(route.defaults?."${it[1]}") {
                // make it optional, as it has default value
                "${prefix}(?:/(?<${it[1]}>[^/]+))?"
            }
            else {
                // required
                "${prefix}(?<${it[1]}>[^/]+)"
            }

        }).join('')

        route.pattern = java.util.regex.Pattern.compile(regexp ? "^${regexp}${suffix}\$" : "^${route.path}\$")
    }
}