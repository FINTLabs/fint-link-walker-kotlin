package no.fintlabs.linkwalker.task

import no.fintlabs.linkwalker.report.ReportService
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tasks")
class TaskController(val taskService: TaskService, val reportService: ReportService) {

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
    fun getTask(@PathVariable id: String): ResponseEntity<Map<String, Any>> {
        val task: Task = taskService.getTask(id) ?: return ResponseEntity.notFound().build()

        val responseMap = mapOf(
                "size" to task.relationErrors.size,
                "relationErrors" to task.relationErrors
        )

        return ResponseEntity.ok(responseMap)
    }

    @GetMapping("/{id}/download")
    fun downloadTaskReports(@PathVariable id: String): ResponseEntity<InputStreamResource> {
        val task: Task = taskService.getTask(id) ?: return ResponseEntity.notFound().build()
        val inputStream = reportService.generateTaskReportExcel(task)

        val headers = HttpHeaders()
        headers.add("Content-Disposition", "attachment; filename=relasjons-test-${task.resourceUri}.xlsx")

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(InputStreamResource(inputStream))
    }

    @PutMapping
    fun clearTasks() {
        taskService.clearTasks()
    }

}