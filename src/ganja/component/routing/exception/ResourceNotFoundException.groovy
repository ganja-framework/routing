package ganja.component.routing.exception

class ResourceNotFoundException extends RuntimeException {

    String path = 'unknown'

    String getMessage() {
        "Route for '${path}' not found."
    }
}
