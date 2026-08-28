package com.company.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StartProcessRequest {
    private String processDefinitionKey;
    private String businessKey;
    private Map<String, Object> variables;
}
