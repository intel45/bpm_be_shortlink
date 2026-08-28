package com.company.bpm.controller;

import com.company.workflow.dto.CompleteTaskRequest;
import com.company.workflow.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "User Tasks Management", description = "Quản lý Nhiệm vụ Người dùng (User Tasks & Assignment)")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Lấy danh sách Công việc (Tasks)", description = "Lọc theo Assignee hoặc Candidate Group/User")
    public ResponseEntity<List<TaskDto>> getTasks(
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String candidateGroup,
            @RequestParam(required = false) String candidateUser,
            @RequestParam(required = false) String processInstanceId) {

        TaskQuery query = taskService.createTaskQuery().initializeFormKeys();

        if (assignee != null && !assignee.isBlank()) {
            query.taskAssignee(assignee);
        }
        if (candidateGroup != null && !candidateGroup.isBlank()) {
            query.taskCandidateGroup(candidateGroup);
        }
        if (candidateUser != null && !candidateUser.isBlank()) {
            query.taskCandidateUser(candidateUser);
        }
        if (processInstanceId != null && !processInstanceId.isBlank()) {
            query.processInstanceId(processInstanceId);
        }

        List<Task> tasks = query.orderByTaskCreateTime().desc().list();

        List<TaskDto> dtos = tasks.stream().map(task -> {
            Map<String, Object> vars = taskService.getVariables(task.getId());
            return TaskDto.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .assignee(task.getAssignee())
                    .owner(task.getOwner())
                    .processInstanceId(task.getProcessInstanceId())
                    .processDefinitionId(task.getProcessDefinitionId())
                    .createTime(task.getCreateTime())
                    .dueDate(task.getDueDate())
                    .priority(task.getPriority())
                    .executionId(task.getExecutionId())
                    .processVariables(vars)
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{taskId}/claim")
    @Operation(summary = "Nhận công việc (Claim Task)")
    public ResponseEntity<Map<String, String>> claimTask(
            @PathVariable String taskId,
            @RequestParam String userId) {
        taskService.claim(taskId, userId);
        return ResponseEntity.ok(Map.of("message", "Đã giao công việc " + taskId + " cho " + userId));
    }

    @PostMapping("/{taskId}/assign")
    @Operation(summary = "Chuyển giao công việc (Delegate / Reassign Task)")
    public ResponseEntity<Map<String, String>> setAssignee(
            @PathVariable String taskId,
            @RequestParam String assignee) {
        taskService.setAssignee(taskId, assignee);
        return ResponseEntity.ok(Map.of("message", "Đã phân công lại cho " + assignee));
    }

    @PostMapping("/{taskId}/complete")
    @Operation(summary = "Hoàn thành công việc (Complete Task)", description = "Cập nhật kết quả & biến đầu ra để chuyển bước BPMN kế tiếp")
    public ResponseEntity<Map<String, String>> completeTask(
            @PathVariable String taskId,
            @RequestBody(required = false) CompleteTaskRequest request) {

        Map<String, Object> variables = (request != null && request.getVariables() != null)
                ? request.getVariables()
                : new HashMap<>();

        if (request != null && request.getUserId() != null) {
            taskService.setAssignee(taskId, request.getUserId());
        }

        taskService.complete(taskId, variables);

        return ResponseEntity.ok(Map.of(
                "message", "Đã hoàn thành công việc thành công!",
                "taskId", taskId
        ));
    }
}
