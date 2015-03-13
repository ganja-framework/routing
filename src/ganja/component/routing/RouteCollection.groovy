package ganja.component.routing

class RouteCollection {

    Map<String, Route> routes = [:]

    void add(String name, Route route) {

        routes.put(name, route)
    }

    Integer size() {
        routes.size()
    }

    void setPrefix(String prefix) {
        routes.values().each({
            it.setPath("${prefix}${it.path}")
        })
    }
}
