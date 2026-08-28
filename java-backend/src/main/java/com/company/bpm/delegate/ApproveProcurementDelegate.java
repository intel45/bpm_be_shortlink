package com.company.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * JavaDelegate xử lý thẩm định & tự động duyệt đơn mua sắm trong Camunda BPM Workflow Engine.
 */
@Component("approveProcurementDelegate")
public class ApproveProcurementDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ApproveProcurementDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        String businessKey = execution.getBusinessKey();
        
        Double totalAmount = (Double) execution.getVariable("totalAmount");
        String requestedBy = (String) execution.getVariable("requestedBy");
        String departmentName = (String) execution.getVariable("departmentName");

        log.info("[Camunda Delegate] Đang xử lý tự động phê duyệt Đơn Mua Sắm PO #{}. Người đề xuất: {}, Số tiền: {} VND, Phòng ban: {}",
                businessKey != null ? businessKey : processInstanceId, requestedBy, totalAmount, departmentName);

        // Logic tự động thẩm định OLA / Ngân sách
        boolean autoApproved = totalAmount != null && totalAmount <= 50000000.0; // Dưới 50M tự động duyệt
        
        execution.setVariable("autoApproved", autoApproved);
        execution.setVariable("approvalStatus", autoApproved ? "APPROVED" : "PENDING_CFO");
        execution.setVariable("processedTimestamp", java.time.LocalDateTime.now().toString());

        log.info("[Camunda Delegate] Hoàn tất JavaDelegate 'ApproveProcurementDelegate'. Kết quả Tự động duyệt: {}", autoApproved);
    }
}
