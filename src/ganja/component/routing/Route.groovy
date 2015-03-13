package ganja.component.routing

import java.util.regex.Pattern

class Route {

    String path = '/'
    Map options = [:]
    List<String> methods = []
    Pattern pattern

    void setPath(String pattern) {
        path = "/${pattern.replaceAll(/^\/*/,'')}" as String
    }

    void setMethods(List<String> values) {

        methods = values*.toUpperCase()

        if(values.contains('GET'))
            methods << 'HEAD'
    }
}
