package no.fintlabs.linkwalker.task

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties("token")
class Task(val url: String, var token: String = "", var clientName: String? = null, val filter: List<String>? = null) {
    var id: String = UUID.randomUUID().toString()
    var org: String? = null
    var status: Status = Status.STARTED
    var requests: AtomicInteger = AtomicInteger(0)
}

enum class Status {
    STARTED,
    FETCHING_RESOURCES,
    CREATING_ENTRY_REPORTS,
    COUNTING_LINKS,
    PROCESSING_LINKS,
    COMPLETED,
    FAILED
}
