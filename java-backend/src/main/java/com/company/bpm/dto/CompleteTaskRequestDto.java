package com.company.bpm.dto;

import java.util.Map;

public class CompleteTaskRequestDto {
    private String action; // APPROVED | REJECTED | RETURN_ADDITIONAL
    private String comment;
    private String approvedBy;
    private Map<String, Object> formValues;

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public Map<String, Object> getFormValues() { return formValues; }
    public void setFormValues(Map<String, Object> formValues) { this.formValues = formValues; }
}
