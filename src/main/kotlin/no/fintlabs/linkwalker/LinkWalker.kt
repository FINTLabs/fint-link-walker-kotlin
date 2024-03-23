package no.fintlabs.linkwalker

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
                        val links = getLinks(task, request)
                    }
                }

        // Process all links
    }

    fun getLinks(task: Task, request: Request): Set<String> {
        task.status = Status.COUNTING_LINKS
        val links: MutableSet<String> = HashSet()

        request._embedded._entries.forEach { entry ->
            entry._links.forEach { (key, link) ->
                if (task.filter != null) {
                    if (task.filter.contains(key)) {
                        link.forEach {
                            links.add(it.href)
                        }
                    }
                } else {
                    link.forEach {
                        links.add(it.href)
                    }
                }
            }
        }

        task.requests.set(links.size)
        return links
    }

}