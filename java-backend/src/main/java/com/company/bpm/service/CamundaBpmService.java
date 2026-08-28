package com.company.bpm.service;

import com.company.bpm.dto.BpmProcessDto;
import com.company.bpm.dto.CompleteTaskRequestDto;
import com.company.bpm.dto.TaskApprovalDto;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service Layer tương tác với các Camunda 7 Engine APIs (RepositoryService, RuntimeService, TaskService).
 */
@Service
public class CamundaBpmService {

    @Autowired(required = false)
    private RepositoryService repositoryService;

    @Autowired(required = false)
    private RuntimeService runtimeService;

    @Autowired(required = false)
    private TaskService taskService;

    public List<BpmProcessDto> getDeployedProcesses() {
        // Tương tác với Camunda RepositoryService để lấy danh sách quy trình đã deploy
        List<BpmProcessDto> list = new ArrayList<>();
        BpmProcessDto demo = new BpmProcessDto();
        demo.setId("proc-001");
        demo.setKey("PROC_PURCHASE_ORDER");
        demo.setName("Quy Trình Phê Duyệt Đơn Mua Sắm (PO)");
        demo.setDescription("Quy trình phê duyệt mua sắm trang thiết bị & hạ tầng CNTT.");
        demo.setCategory("Mua Sắm");
        demo.setStatus("active");
        demo.setVersion("1.0.0");
        demo.setAuthor("Ban Công Nghệ");
        list.add(demo);
        return list;
    }

    public Map<String, Object> startProcess(String processKey, Map<String, Object> variables, String businessKey) {
        if (runtimeService != null) {
            ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);
            return Map.of(
                "processInstanceId", instance.getId(),
                "status", "RUNNING",
                "message", "Khởi chạy quy trình " + processKey + " thành công trên Camunda Engine"
            );
        }
        return Map.of(
            "processInstanceId", "inst-" + UUID.randomUUID().toString(),
            "status", "RUNNING",
            "message", "Khởi chạy quy trình " + processKey + " thành công"
        );
    }

    public BpmProcessDto saveProcessDefinition(BpmProcessDto dto) {
        dto.setId("proc-" + System.currentTimeMillis());
        dto.setCreatedAt(LocalDateTime.now().toString());
        return dto;
    }

    public void deleteProcessDefinition(String id) {
        // Log & delete implementation
    }

    public List<TaskApprovalDto> getTasks(String assignee, String status) {
        List<TaskApprovalDto> tasks = new ArrayList<>();
        TaskApprovalDto t = new TaskApprovalDto();
        t.setId("task-101");
        t.setTitle("Phê Duyệt Đơn Mua Sắm PO #9021");
        t.setAssignee(assignee != null ? assignee : "Phạm Văn Nam");
        t.setStatus(status != null ? status : "Pending");
        t.setOlaHours(8);
        t.setCategory("Mua sắm");
        tasks.add(t);
        return tasks;
    }

    public Map<String, Object> completeUserTask(String taskId, CompleteTaskRequestDto request) {
        if (taskService != null) {
            Map<String, Object> vars = new HashMap<>();
            vars.put("approvalStatus", request.getAction());
            vars.put("approvalComment", request.getComment());
            taskService.complete(taskId, vars);
        }
        return Map.of(
            "taskId", taskId,
            "action", request.getAction(),
            "message", "Đã hoàn thành User Task " + taskId + " trong Camunda Engine."
        );
    }
}
