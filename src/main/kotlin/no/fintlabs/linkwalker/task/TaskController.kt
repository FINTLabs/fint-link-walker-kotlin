package no.fintlabs.linkwalker.task

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(val taskService: TaskService) {

    @PostMapping
    fun postTask(@RequestBody task: Task,
                 @RequestHeader("x-org-id", required = true) organisation: String,
                 @RequestHeader("Authorization", required = false) authToken: String?): ResponseEntity<Any> {
        if (authToken == null && task.clientName == null) {
            return ResponseEntity.badRequest().body("Auth header or client name in body must be set")
        }
        taskService.startTask(task, organisation, authToken)
        return ResponseEntity.accepted().body(task)
    }

    @GetMapping
    fun getTasks(): ResponseEntity<Collection<Task>> {
        return ResponseEntity.ok(taskService.getTasks())
    }

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: String): ResponseEntity<Task> {
        val task: Task = taskService.getTask(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(task)
    }

    @PutMapping
    fun clearTasks() {
        taskService.clearTasks()
    }

}