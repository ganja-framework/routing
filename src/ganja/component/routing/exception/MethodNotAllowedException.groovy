package ganja.component.routing.exception

class MethodNotAllowedException extends RuntimeException {

    protected String method

    protected List<String> allowed

    protected Integer statusCode = 405

    String getMessage() {
        "Method '${method}' is not allowed. Allowed methods: ${allowed}"
    }
}
