package com.company.bpm.dto;


import lombok.Data;

import java.util.Map;

@Data
public class StartReq {
    private String soCongVan;
    private String soDen;
    private String trichYeu;
    private String loai;
    private String doMat;
    private String doKhan;
    private String  NguoiXuLy;

    String processKey;
    String businessKey;
    String initiator;
    String departmentId;
    String priority;
    Map<String, Object> variables;

}
