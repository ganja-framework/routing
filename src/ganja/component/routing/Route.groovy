package ganja.component.routing

class Route {

    protected String path = '/'

    protected Map options = [:]

    protected List<String> methods = []

    void setPath(String pattern) {
        path = "/${pattern.replaceAll(/^\/*/,'')}"
    }

    void setMethods(List<String> values) {

        methods = values*.toUpperCase()

        if(values.contains('GET'))
            methods << 'HEAD'
    }
}
