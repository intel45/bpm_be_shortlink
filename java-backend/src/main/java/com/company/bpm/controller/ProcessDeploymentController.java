package com.company.bpm.controller;

import com.company.workflow.dto.ProcessDefinitionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/processes")
@RequiredArgsConstructor
@Tag(name = "BPMN Deployment & Definitions", description = "Quản lý triển khai quy trình BPMN Camunda")
public class ProcessDeploymentController {

    private final RepositoryService repositoryService;

    @PostMapping(value = "/deploy-xml", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Triển khai Quy trình từ chuỗi BPMN XML", description = "Lưu và kích hoạt định nghĩa quy trình trong Camunda Engine")
    public ResponseEntity<Map<String, Object>> deployXml(@RequestBody Map<String, String> request) {
        String processName = request.getOrDefault("name", "Process_" + System.currentTimeMillis());
        String xmlContent = request.get("xml");

        if (xmlContent == null || xmlContent.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nội dung XML BPMN không được trống"));
        }

        Deployment deployment = repositoryService.createDeployment()
                .name(processName)
                .addString(processName + ".bpmn", xmlContent)
                .deploy();

        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Triển khai quy trình BPMN thành công!",
                "deploymentId", deployment.getId(),
                "processDefinitionId", definition != null ? definition.getId() : "",
                "processDefinitionKey", definition != null ? definition.getKey() : "",
                "version", definition != null ? definition.getVersion() : 1
        ));
    }

    @PostMapping(value = "/deploy-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Triển khai File BPMN 2.0 (.bpmn/.xml)")
    public ResponseEntity<Map<String, Object>> deployFile(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            Deployment deployment = repositoryService.createDeployment()
                    .name(filename)
                    .addInputStream(filename, file.getInputStream())
                    .deploy();

            return ResponseEntity.ok(Map.of(
                    "deploymentId", deployment.getId(),
                    "name", deployment.getName(),
                    "deploymentTime", deployment.getDeploymentTime()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/definitions")
    @Operation(summary = "Lấy danh sách Quy trình đã triển khai (Latest Versions)")
    public ResponseEntity<List<ProcessDefinitionDto>> getLatestDefinitions() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .latestVersion()
                .orderByProcessDefinitionName()
                .asc()
                .list();

        List<ProcessDefinitionDto> dtos = list.stream().map(pd -> ProcessDefinitionDto.builder()
                .id(pd.getId())
                .key(pd.getKey())
                .name(pd.getName())
                .version(pd.getVersion())
                .category(pd.getCategory())
                .deploymentId(pd.getDeploymentId())
                .isSuspended(pd.isSuspended())
                .build()).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/definitions/{key}/xml")
    @Operation(summary = "Lấy nội dung sơ đồ BPMN XML theo Process Key")
    public ResponseEntity<String> getBpmnXml(@PathVariable String key) {
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(key)
                .latestVersion()
                .singleResult();

        if (pd == null) {
            return ResponseEntity.notFound().build();
        }

        try (InputStream inputStream = repositoryService.getProcessModel(pd.getId())) {
            String xml = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(xml);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
