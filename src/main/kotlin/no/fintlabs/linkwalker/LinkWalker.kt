package no.fintlabs.linkwalker

import no.fintlabs.linkwalker.task.Task
import org.springframework.stereotype.Service

@Service
class LinkWalker {

    fun processTaskWithClientName(task: Task) {
        // Fetch client credentials through Kafka

        // Fetch bearer token for client

        // Process task
    }

    fun processTask(task: Task) {
        // Perform request to task.url with token

        // Count Links that needs processing

        // Process all links
    }

}