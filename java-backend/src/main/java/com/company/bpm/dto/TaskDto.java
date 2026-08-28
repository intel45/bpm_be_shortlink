package com.company.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private String id;
    private String name;
    private String assignee;
    private String owner;
    private String processInstanceId;
    private String processDefinitionId;
    private Date createTime;
    private Date dueDate;
    private int priority;
    private String executionId;
    private Map<String, Object> taskLocalVariables;
    private Map<String, Object> processVariables;
}
