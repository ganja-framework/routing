package ganja.component.routing.utils

class Pattern {

    def extract(String path) {

        (path =~ /\{\w+\}/).collect({ it[1..-2] })
    }
}
