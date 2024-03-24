package no.fintlabs.linkwalker.report

import org.springframework.http.HttpStatusCode

class EntryReport {

    var parentLinks: MutableSet<String> = HashSet()
    var relationLinks: MutableSet<String> = HashSet()
    var relationErrors: MutableList<RelationError> = ArrayList()
    var validateSelfLinks: Boolean = true

}

class RelationError(val relationLink: String, val httpStatusCode: HttpStatusCode) {
}