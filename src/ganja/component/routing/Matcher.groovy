package ganja.component.routing

import ganja.component.routing.exception.MethodNotAllowedException
import ganja.component.routing.exception.ResourceNotFoundException
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

                    throw new MethodNotAllowedException(method: method, allowed: route.methods)
                }
                else {

                    route.pattern.namedGroups().each({

                        params.put(it.key, matcher.group(it.value))
                    })

                    return [ route: name ] + route.defaults + params
                }
            }
        }

        throw new ResourceNotFoundException(path: input)
    }
}
