package no.fintlabs.linkwalker

import no.fintlabs.linkwalker.report.EntryReport
import no.fintlabs.linkwalker.report.RelationError
import no.fintlabs.linkwalker.request.Link
import no.fintlabs.linkwalker.request.Request
import no.fintlabs.linkwalker.request.RequestService
import no.fintlabs.linkwalker.task.Status
import no.fintlabs.linkwalker.task.Task
import org.springframework.stereotype.Service

@Service
class LinkWalker(val requestService: RequestService) {

    fun processTaskWithClientName(task: Task) {
        // Fetch client credentials through Kafka

        // Fetch bearer token for client

        // Process task
    }

    fun processTask(task: Task) {
        task.status = Status.FETCHING_RESOURCES
        requestService.getRequest(task.url, task.token)
                .subscribe(
                        { request ->
                            createEntryReports(task, request)
                            countRelationLinks(task)
                            processRelationLinks(task)
                        },
                        { _ ->
                            task.status = Status.FAILED
                        }
                )
    }

    fun processRelationLinks(task: Task) {
        task.status = Status.PROCESSING_LINKS

        if (task.entryReports.all { it.relationLinks.isEmpty() }) {
            task.status = Status.COMPLETED
            return
        }

        task.entryReports.forEach { entryReport ->
            entryReport.relationLinks.forEach { url ->
                requestService.getStatus(url, task.token).subscribe { statusCode ->
                    task.requests.decrementAndGet()
                    if (statusCode.isError) {
                        entryReport.relationErrors.add(RelationError(url, statusCode))
                    }
                    if (task.isCompleted()) {
                        task.status = Status.COMPLETED
                    }
                }
            }
        }
    }

    fun countRelationLinks(task: Task) {
        task.status = Status.COUNTING_REQUESTS

        task.entryReports.forEach {
            it.relationLinks.forEach { _ ->
                task.requests.incrementAndGet()
            }
        }
    }

    fun createEntryReports(task: Task, request: Request): List<EntryReport> {
        val entryReports: MutableList<EntryReport> = ArrayList()
        task.status = Status.CREATING_ENTRY_REPORTS

        request._embedded._entries.forEach { entry ->
            val entryReport = createEntryReport(task, entry._links["self"])
            entryReports.add(entryReport)
            entry._links.forEach { (key, links) ->
                if (task.filter == null || task.filter.contains(key)) {
                    links.forEach {
                        entryReport.relationLinks.add(it.href)
                    }
                }
            }
        }

        return entryReports
    }

    fun createEntryReport(task: Task, selfLinks: List<Link>?): EntryReport {
        val entryReport = EntryReport()

        selfLinks?.forEach {
            entryReport.parentLinks.add(it.href)
        }

        if (task.filter != null) {
            entryReport.validateSelfLinks = task.filter.contains("self")
        }

        return entryReport
    }

}