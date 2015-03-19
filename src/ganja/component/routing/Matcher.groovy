package ganja.component.routing

import ganja.common.http.exception.MethodNotAllowedException
import ganja.common.http.exception.NotFoundException
import ganja.component.routing.utils.RouteCompiler

class Matcher {

    RouteCompiler compiler
    RouteCollection collection

    Map match(String input, String method = 'GET') {

        String path = "/${input.replaceAll(/^\/*/,'')}"

        for(entry in collection) {

            def route = entry.value
            def name = entry.key

            if( ! route.pattern) {
                compiler.compile(route)
            }

            def matcher = (path =~ route.pattern)

            if(matcher.matches()) {

                Map params = [:]

                if(route.methods && ! route.methods.contains(method.toUpperCase())) {

                    throw new MethodNotAllowedException("Method '${method}' is not allowed. Allowed methods: ${route.methods}")
                }
                else {

                    route.pattern.namedGroups().each({

                        params.put(it.key, matcher.group(it.value))
                    })

                    return [ route: name ] + route.defaults + params
                }
            }
        }

        throw new NotFoundException("Route for path: '${path}' not found")
    }
}
