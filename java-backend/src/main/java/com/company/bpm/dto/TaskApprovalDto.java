package com.company.bpm.dto;

import java.util.Map;

public class TaskApprovalDto {
    private String id;
    private String title;
    private String assignee;
    private String status;
    private int olaHours;
    private String category;
    private String createdAt;
    private Map<String, Object> formValues;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAssignee() { return assignee; }
    public void setAssignee(String assignee) { this.assignee = assignee; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getOlaHours() { return olaHours; }
    public void setOlaHours(int olaHours) { this.olaHours = olaHours; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public Map<String, Object> getFormValues() { return formValues; }
    public void setFormValues(Map<String, Object> formValues) { this.formValues = formValues; }
}
