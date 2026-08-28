package com.company.bpm.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * JavaDelegate gửi email & notification real-time cho nhân sự khi có nhiệm vụ/quy trình mới.
 */
@Component("sendNotificationDelegate")
public class SendNotificationDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(SendNotificationDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String assignee = (String) execution.getVariable("assignee");
        String taskTitle = (String) execution.getVariable("taskTitle");
        String email = (String) execution.getVariable("assigneeEmail");

        log.info("[Camunda Delegate] Gửi thông báo đến Assignee: {} ({}) cho nhiệm vụ: '{}'", assignee, email, taskTitle);

        // Giả lập gửi Email SMTP / WebSocket Push Notification
        boolean notificationSent = true;

        execution.setVariable("notificationSent", notificationSent);
        execution.setVariable("notifiedAt", java.time.LocalDateTime.now().toString());
    }
}
