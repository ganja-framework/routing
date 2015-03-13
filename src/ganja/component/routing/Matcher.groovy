package ganja.component.routing

import ganja.component.routing.exception.MethodNotAllowedException
import ganja.component.routing.exception.ResourceNotFoundException
import ganja.component.routing.utils.Pattern
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

            if(path ==~ route.pattern) {

                if(route.methods && ! route.methods.contains(method.toUpperCase())) {

                    throw new MethodNotAllowedException(method: method, allowed: route.methods)
                }
                else {

                    return route.options + [ route: name ]
                }
            }
        }

        throw new ResourceNotFoundException(path: input)
    }
}
