package com.company.bpm.dto;

import java.util.List;

public class BulkImportEmployeesRequestDto {
    private List<EmployeeDto> employees;

    public List<EmployeeDto> getEmployees() { return employees; }
    public void setEmployees(List<EmployeeDto> employees) { this.employees = employees; }
}
