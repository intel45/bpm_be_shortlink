package com.company.bpm.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CompleteReq {

    String taskId;
    String action;
    String comment;
    String ketQua;
    Map<String, Object> variables;

}
