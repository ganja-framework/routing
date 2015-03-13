package ganja.component.routing

class RouteCollection {

    @Delegate Map<String, Route> routes = [:]

    void add(String name, Route route) {

        routes.put(name, route)
    }

    void setPrefix(String prefix) {
        routes.values().each({
            it.setPath("${prefix}${it.path}")
        })
    }
}
