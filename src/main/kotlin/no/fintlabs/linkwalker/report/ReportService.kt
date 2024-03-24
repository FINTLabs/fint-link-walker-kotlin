package no.fintlabs.linkwalker.report

import no.fintlabs.linkwalker.task.Task
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@Service
class ReportService {

    fun generateTaskReportExcel(task: Task): ByteArrayInputStream {
        val workbook: Workbook = XSSFWorkbook()
        try {
            val sheet = workbook.createSheet("Report")

            val headerRow = sheet.createRow(0)
            headerRow.createCell(0).setCellValue("URL")
            headerRow.createCell(1).setCellValue("Status Code")
            headerRow.createCell(2).setCellValue("Parent Urls")

            var rowCount = 1
            task.entryReports.forEach { entryReport ->
                entryReport.relationErrors.forEach { relationError ->
                    val row = sheet.createRow(rowCount++)
                    row.createCell(0).setCellValue(relationError.relationLink)
                    row.createCell(1).setCellValue(relationError.httpStatusCode.value().toDouble())
                    row.createCell(2).setCellValue(entryReport.parentLinks.toString())
                }
            }

            val outputStream = ByteArrayOutputStream()
            workbook.write(outputStream)
            return ByteArrayInputStream(outputStream.toByteArray())
        } finally {
            workbook.close()
        }
    }

}