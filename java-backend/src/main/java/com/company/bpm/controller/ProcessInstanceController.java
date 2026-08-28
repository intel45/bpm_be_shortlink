package com.company.bpm.controller;

import com.company.bpm.dto.BpmProcessDto;
import com.company.bpm.dto.StartProcessRequestDto;
import com.company.bpm.service.CamundaBpmService;
import io.swagger.v3.oas.annotations.Operation;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Spring Boot REST Controller kết nối Frontend với Camunda BPM Engine (BPMN 2.0).
 */
@RestController
@RequestMapping("/api/bpm/processes")
@CrossOrigin(origins = "*")
public class ProcessInstanceController {

    @Autowired
    private CamundaBpmService camundaBpmService;


    @Autowired
    private RuntimeService runtimeService;


    /**
     * GET /api/bpm/processes - Lấy danh sách định nghĩa quy trình BPMN
     */
//    @GetMapping
//    public ResponseEntity<List<BpmProcessDto>> getAllProcesses() {
//        List<BpmProcessDto> processes = camundaBpmService.getDeployedProcesses();
//        return ResponseEntity.ok(processes);
//    }

    /**
     * POST /api/bpm/processes/start - Khởi chạy một Process Instance mới trong Camunda Engine
     */
//    @PostMapping("/start")
//    public ResponseEntity<Map<String, Object>> startProcessInstance(@RequestBody StartProcessRequestDto request) {
//        Map<String, Object> response = camundaBpmService.startProcess(request.getProcessKey(), request.getVariables(), request.getBusinessKey());
//        return ResponseEntity.ok(response);
//    }

    /**
     * POST /api/bpm/processes - Lưu/Tạo quy trình BPMN mới
     */
    @PostMapping
    public ResponseEntity<BpmProcessDto> createProcess(@RequestBody BpmProcessDto processDto) {
        BpmProcessDto created = camundaBpmService.saveProcessDefinition(processDto);
        return ResponseEntity.ok(created);
    }

    /**
     * DELETE /api/bpm/processes/{id} - Xoá quy trình
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProcess(@PathVariable String id) {
        camundaBpmService.deleteProcessDefinition(id);
        return ResponseEntity.ok(Map.of("message", "Đã xoá quy trình BPMN thành công", "id", id));
    }

    @PostMapping("/start")
    @Operation(summary = "Bắt đầu 1 Instance Quy trình mới", description = "Khởi chạy quy trình theo Process Key kèm biến đầu vào (Process Variables)")
    public ResponseEntity<Map<String, Object>> startInstance(@RequestBody com.company.workflow.dto.StartProcessRequest request) {
        if (request.getProcessDefinitionKey() == null || request.getProcessDefinitionKey().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "processDefinitionKey không được trống"));
        }

        Map<String, Object> vars = request.getVariables() != null ? request.getVariables() : new HashMap<>();

        ProcessInstance instance;
        if (request.getBusinessKey() != null && !request.getBusinessKey().isBlank()) {
            instance = runtimeService.startProcessInstanceByKey(request.getProcessDefinitionKey(), request.getBusinessKey(), vars);
        } else {
            instance = runtimeService.startProcessInstanceByKey(request.getProcessDefinitionKey(), vars);
        }

        return ResponseEntity.ok(Map.of(
                "message", "Khởi tạo quy trình thành công!",
                "processInstanceId", instance.getId(),
                "businessKey", instance.getBusinessKey() != null ? instance.getBusinessKey() : "",
                "processDefinitionId", instance.getProcessDefinitionId(),
                "isEnded", instance.isEnded()
        ));
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách Instance đang chạy (Active Instances)")
    public ResponseEntity<List<Map<String, Object>>> getActiveInstances() {
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
                .active()
                .orderByProcessInstanceId()
                .desc()
                .list();

        List<Map<String, Object>> result = instances.stream().map(pi -> Map.of(
                "id", (Object) pi.getId(),
                "processDefinitionId", pi.getProcessDefinitionId(),
                "businessKey", pi.getBusinessKey() != null ? pi.getBusinessKey() : "",
                "isEnded", pi.isEnded(),
                "isSuspended", pi.isSuspended()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{instanceId}/variables")
    @Operation(summary = "Lấy toàn bộ biến quy trình (Process Variables) của Instance")
    public ResponseEntity<Map<String, Object>> getVariables(@PathVariable String instanceId) {
        Map<String, Object> variables = runtimeService.getVariables(instanceId);
        return ResponseEntity.ok(variables);
    }

    @PostMapping("/{instanceId}/variables")
    @Operation(summary = "Cập nhật / Thêm biến mới cho Instance quy trình")
    public ResponseEntity<Map<String, String>> setVariables(
            @PathVariable String instanceId,
            @RequestBody Map<String, Object> variables) {
        runtimeService.setVariables(instanceId, variables);
        return ResponseEntity.ok(Map.of("message", "Đã cập nhật " + variables.size() + " biến quy trình."));
    }
}
