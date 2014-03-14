package ganja.component.routing.exception

class MethodNotAllowedException extends RuntimeException {

    protected String method

    protected List<String> allowed

    String getMessage() {
        "Method '${method}' is not allowed. Allowed methods: ${allowed}"
    }
}
