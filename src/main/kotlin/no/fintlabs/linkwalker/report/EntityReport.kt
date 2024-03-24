package no.fintlabs.linkwalker.report

import org.springframework.http.HttpStatusCode

class EntityReport {

    var parentLinks: MutableSet<String> = HashSet()
    var relationLinks: MutableSet<String> = HashSet()
    var relationErrors: List<RelationError> = ArrayList()

}

class RelationError(val relationLink: String, val httpStatusCode: HttpStatusCode, val errorMessage: String) {
}