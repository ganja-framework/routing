package ganja.component.routing.exception

class ResourceNotFoundException extends RuntimeException {

    protected String path = 'unknown'
    protected Integer statusCode = 404

    String getMessage() {
        "Route for '${path}' not found."
    }
}
