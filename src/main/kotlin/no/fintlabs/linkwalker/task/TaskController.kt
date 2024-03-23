package no.fintlabs.linkwalker.task

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks/{organisation}")
class TaskController(val taskService: TaskService) {

    @PostMapping
    fun postLink(@RequestBody task: Task,
                 @PathVariable organisation: String,
                 @RequestHeader("Authorization") authToken: String?): ResponseEntity<Any> {
        if (authToken == null || task.clientName == null) {
            return ResponseEntity.badRequest().body("Auth header or client name must be set")
        }
        taskService.startTask(task, organisation, authToken)
        return ResponseEntity.accepted().body(task)
    }

    @PutMapping
    fun clearTasks() {
        taskService.clearTasks()
    }

}