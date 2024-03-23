package no.fintlabs.linkwalker.task

import no.fintlabs.linkwalker.LinkWalker
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class TaskService(val linkWalker: LinkWalker) {

    private val taskCache: LinkedHashMap<String, Task> = LinkedHashMap()

    @Async
    fun startTask(task: Task, organisation: String, authToken: String?) {
        taskCache[task.id] = task

        if (authToken != null) {
            // TODO: Validate token
            task.token = authToken.replace("Bearer ", "")
            linkWalker.processTaskWithToken(task)
        } else {
            task.org = organisation
            linkWalker.processTaskWithClientName(task)
        }
    }

    fun getTasks(): Collection<Task> {
        return taskCache.values
    }

    fun getTask(id: String): Task? {
        return taskCache[id]
    }

    fun clearTasks() {
        taskCache.clear()
    }

}