package ganja.component.routing

import ganja.component.routing.exception.MethodNotAllowedException
import ganja.component.routing.exception.ResourceNotFoundException

class Matcher {

    protected RouteCollection collection

    Map match(String pattern, String method = 'GET') {

        String path = "/${pattern.replaceAll(/^\/*/,'')}"

        for(route in collection.values()) {

            if(path == route.path) {

                if(route.methods && ! route.methods.contains(method.toUpperCase())) {

                    throw new MethodNotAllowedException(method: method, allowed: route.methods)
                }
                else {

                    return route.options
                }
            }
        }

        throw new ResourceNotFoundException(path: pattern)
    }
}
