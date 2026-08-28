package com.company.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessDefinitionDto {
    private String id;
    private String key;
    private String name;
    private int version;
    private String category;
    private String deploymentId;
    private boolean isSuspended;
}
