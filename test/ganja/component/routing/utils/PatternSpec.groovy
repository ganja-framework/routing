package ganja.component.routing.utils

import spock.lang.Specification

class PatternSpec extends Specification {

    void "it can extract all parameters from path"() {

        given:
        def subject = new Pattern()

        expect:
        [ 'foo' ] == subject.extract('/page/{foo}')
        [ 'foo', 'bar' ] == subject.extract('/page/{foo}/{bar}')
        [ 'page', 'foo', 'bar' ] == subject.extract('/{page}/{foo}/{bar}')
        [ 'foo', 'bar' ] == subject.extract('/page/{foo}.{bar}')
    }
}
