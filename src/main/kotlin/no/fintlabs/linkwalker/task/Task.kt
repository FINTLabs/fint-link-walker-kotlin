package no.fintlabs.linkwalker.task

import java.util.*
import java.util.concurrent.atomic.AtomicInteger

class Task(val url: String, var token: String? = null, var clientName: String? = null) {
    var org: String? = null
    var id: String = UUID.randomUUID().toString()
    var status: Status = Status.STARTED
    var requests: AtomicInteger = AtomicInteger(0)
}

enum class Status {
    STARTED,
    FETCHING_RESOURCES,
    PROCESSING_LINKS,
    COMPLETED,
    FAILED
}