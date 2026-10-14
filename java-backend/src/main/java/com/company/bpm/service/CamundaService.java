package com.company.bpm.service;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CamundaService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    // Start process
    public void startCongVan(String nguoiXuLy) {
        Map<String, Object> vars = new HashMap<>();
        vars.put("nguoiXuLy", nguoiXuLy);
        vars.put("ketQua", "MOI");

        runtimeService.startProcessInstanceByKey(
                "CongVanDenProcess", vars
        );
    }

    // Lấy task theo user
    public List<Task> getTasks(String user) {
        return taskService.createTaskQuery()
                .taskAssignee(user)
                .list();
    }

    // Complete task
    public void complete(String taskId, String ketQua) {
        Map<String, Object> vars = Map.of("ketQua", ketQua);
        taskService.complete(taskId, vars);
    }

    public void claimTask(String taskId, String userId) {

        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        if (task == null) {
            throw new RuntimeException("Task not found: " + taskId);
        }

        // Nếu task đã có người xử lý
        if (task.getAssignee() != null) {
            throw new RuntimeException("Task already claimed by " + task.getAssignee());
        }
        taskService.claim(taskId, userId);
    }
}
