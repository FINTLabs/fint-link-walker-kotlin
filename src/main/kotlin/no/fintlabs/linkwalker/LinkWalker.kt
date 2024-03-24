package no.fintlabs.linkwalker

import no.fintlabs.linkwalker.report.EntryReport
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
        requestService.get(task.url, task.token)
                .subscribe { request ->
                    run {
                        val entryReports = createEntryReports(task, request)
                    }
                }

        // Process all links
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