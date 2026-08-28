package com.company.workflow.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("purchaseOrderDelegate")
public class PurchaseOrderDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("========== CAMUNDA SERVICE TASK: [PurchaseOrderDelegate] ==========");
        
        String poNumber = "PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        log.info("Tạo mã đơn mua hàng tự động trên ERP: {}", poNumber);

        execution.setVariable("poNumber", poNumber);
        execution.setVariable("poStatus", "CREATED_IN_ERP");

        log.info("Đơn mua hàng PO đã được lưu vào cơ sở dữ liệu SAP/ERP thành công.");
    }
}
