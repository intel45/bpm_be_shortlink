package com.company.workflow.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("updateLeaveDelegate")
public class UpdateLeaveDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(UpdateLeaveDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("========== CAMUNDA SERVICE TASK: [UpdateLeaveDelegate] ==========");
        
        String employeeName = (String) execution.getVariable("employeeName");
        Object leaveDaysObj = execution.getVariable("leaveDays");
        String processInstanceId = execution.getProcessInstanceId();

        log.info("Xử lý cập nhật số ngày phép vào hệ thống HR.");
        log.info("Nhân viên: {}, Số ngày xin nghỉ: {}, Process Instance: {}", employeeName, leaveDaysObj, processInstanceId);

        // Giả lập logic tích hợp API hệ thống HR nội bộ
        execution.setVariable("hrSystemUpdated", true);
        execution.setVariable("hrUpdateTimestamp", LocalDateTime.now().toString());
        execution.setVariable("hrStatus", "APPROVED_AND_LOGGED");

        log.info("Cập nhật dữ liệu HR hoàn tất thành công.");
    }
}
