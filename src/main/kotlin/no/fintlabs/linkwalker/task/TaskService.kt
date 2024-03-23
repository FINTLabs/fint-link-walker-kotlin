package no.fintlabs.linkwalker.task

import no.fintlabs.linkwalker.LinkWalker
import org.springframework.stereotype.Service

@Service
class TaskService(val linkWalker: LinkWalker) {

    private val taskCache: LinkedHashMap<String, Task> = LinkedHashMap()

    fun startTask(task: Task, organisation: String, authToken: String?) {
        task.org = organisation
        taskCache[task.id] = task

        if (authToken != null) {
            task.token = authToken.replace("Bearer ", "")
            linkWalker.processTaskWithToken(task)
        } else {
            linkWalker.processTaskWithClientName(task)
        }
    }

}