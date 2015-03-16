package ganja.component.routing.utils

import spock.lang.Specification
import spock.lang.Unroll

class PatternSpec extends Specification {

    void "it can extract all parameters from path"() {

        given:
        def subject = new Pattern()

        expect:
        [['{foo}', 'foo']] == subject.extract('/page/{foo}')
        [['{foo}', 'foo'], ['{bar}', 'bar']] == subject.extract('/page/{foo}/{bar}')
        [['{page}', 'page'], ['{foo}', 'foo'], ['{bar}', 'bar']] == subject.extract('/{page}/{foo}/{bar}')
        [['{foo}', 'foo'], ['{bar}', 'bar']] == subject.extract('/page/{foo}.{bar}')
    }

    @Unroll("#input matches #pattern")
    void "test path matching"() {

        expect:
        java.util.regex.Pattern.compile(pattern).matcher(input).matches()

        where:
        input                   | pattern
        '/foo'                  | '/(?<foo>[^/]+)'
        '/bar'                  | '/(?<foo>[^/]+)'
        '/1345'                 | '/(?<foo>[^/]+)'
        '/foo'                  | '/(?<foo>[^/]+)(?:/(?<bar>[^/]+))?'
        '/foo/bar'              | '/(?<foo>[^/]+)(?:/(?<bar>[^/]+))?'
        '/foo?id=78cad'         | '/(?<foo>[^/]+)(?:/(?<bar>[^/]+))?'
        '/foo/?id=78cad'        | '/(?<foo>[^/]+)(?:/(?<bar>[^/]+))?'
        '/foo/bar?id=14'        | '/(?<foo>[^/]+)(?:/(?<bar>[^/]+))?'
        '/admin/pages/134'      | '/admin/pages/(?<foo>[^/]+)'
        '/admin/pages/134/edit' | '/admin/pages/(?<foo>[^/]+)(?:/(?<bar>[^/]+))?'
        '/cms/pages/134/edit'   | '/(?<module>[^/]+)/pages/(?<pageId>[^/]+)/(?<action>[^/]+)'
    }
}
