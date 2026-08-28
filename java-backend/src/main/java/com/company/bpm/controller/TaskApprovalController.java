package com.company.bpm.controller;

import com.company.bpm.dto.TaskApprovalDto;
import com.company.bpm.dto.CompleteTaskRequestDto;
import com.company.bpm.service.CamundaBpmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Spring Boot REST Controller quản lý Nhiệm vụ (User Tasks) và Phê duyệt trong Camunda Tasklist.
 */
@RestController
@RequestMapping("/api/bpm/tasks")
@CrossOrigin(origins = "*")
public class TaskApprovalController {

    @Autowired
    private CamundaBpmService camundaBpmService;

    /**
     * GET /api/bpm/tasks - Lấy danh sách nhiệm vụ chờ xử lý (Camunda Tasklist)
     */
    @GetMapping
    public ResponseEntity<List<TaskApprovalDto>> getPendingTasks(
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String status) {
        List<TaskApprovalDto> tasks = camundaBpmService.getTasks(assignee, status);
        return ResponseEntity.ok(tasks);
    }

    /**
     * POST /api/bpm/tasks/{taskId}/complete - Phê duyệt hoặc Từ chối User Task trong Camunda Engine
     */
    @PostMapping("/{taskId}/complete")
    public ResponseEntity<Map<String, Object>> completeTask(
            @PathVariable String taskId,
            @RequestBody CompleteTaskRequestDto request) {
        Map<String, Object> result = camundaBpmService.completeUserTask(taskId, request);
        return ResponseEntity.ok(result);
    }
}
