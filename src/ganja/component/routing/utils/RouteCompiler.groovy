package ganja.component.routing.utils

import ganja.component.routing.Route

class RouteCompiler {

    Pattern pattern

    void compile(Route route) {

        List variables = []
        List tokens = []
        Integer position = 0

        String regexp = pattern.extract(route.path).collect({

            if(variables.contains(it[1])) {
                throw new IllegalArgumentException("Route pattern \"${route.path}\" cannot reference variable name \"${it[1]}\" more than once.")
            }

            variables.add(it[1])

            def prefix = route.path.substring(position, route.path.indexOf(it[0]))
            def suffix = route.path.substring(route.path.indexOf(it[0]) + it[0].size())

            position = route.path.indexOf(it[0]) + it[0].size()

//            println "Var: ${it[0]}, prefix: ${prefix}, suffix: ${suffix}, after position: ${position}, indexOf it: ${route.path.indexOf(it[0])}, size: ${it[0].size()}"

            if(route.defaults?."${it[1]}") {
                "${prefix}(?:/(?<${it[1]}>[^/]+))?"
            }
            else {
                "${prefix}(?<${it[1]}>[^/]+)"
            }

        }).join('')

        route.pattern = java.util.regex.Pattern.compile(regexp)
    }
}