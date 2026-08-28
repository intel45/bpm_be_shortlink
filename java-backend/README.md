# Java Spring Boot 3.2 + Camunda 7 Workflow Backend

Dự án Backend REST API kết hợp **Spring Boot 3.2 (Java 17)** và quy trình **Camunda BPM 7.21** cho hệ thống quản lý sơ đồ BPMN.

---

## 🚀 Tính năng Backend
1. **Triển khai BPMN (Process Deployment)**:
   - Deploy trực tiếp từ file `.bpmn` hoặc chuỗi BPMN XML nhận từ Frontend.
   - Truy vấn sơ đồ XML để render lại trên trình thiết kế `bpmn-js`.
2. **Khởi tạo & Quản lý Instance (Process Execution)**:
   - Khởi chạy Instance quy trình bằng `processDefinitionKey` kèm biến (variables).
   - Truy vấn danh sách Instance đang chạy & xem biến trạng thái.
3. **Nhiệm vụ Người dùng (User Tasks)**:
   - Lấy danh sách nhiệm vụ theo `assignee`, `candidateGroup`, `candidateUser`.
   - Phân công lại nhiệm vụ (`claim`, `assign`).
   - Hoàn thành nhiệm vụ (`complete`) để kích hoạt luồng kế tiếp.
4. **Camunda Service Task Delegates**:
   - Mẫu Java Delegate `UpdateLeaveDelegate` và `PurchaseOrderDelegate` tích hợp logic nghiệp vụ thực tế.
5. **Giao diện Quản trị**:
   - **Camunda Cockpit & Tasklist**: `http://localhost:8080/camunda`
   - **Swagger OpenAPI Documentation**: `http://localhost:8080/swagger-ui.html`
   - **H2 Database Console**: `http://localhost:8080/h2-console`

---

## 🛠 Hướng dẫn Khởi chạy (Getting Started)

### Yêu cầu hệ thống:
- **Java**: Development Kit 17 hoặc 21
- **Maven**: 3.8+ (hoặc dùng `./mvnw`)

### 1. Chạy ứng dụng Spring Boot:
```bash
cd backend
mvn spring-boot:run
```

### 2. Đăng nhập Camunda Cockpit:
- URL: `http://localhost:8080/camunda`
- **Username**: `admin`
- **Password**: `adminpassword`

---

## 📡 Danh sách REST API dành cho Frontend (FE Call)

### 1. Deployment & Definitions
- `POST /api/v1/processes/deploy-xml`: Deploy BPMN XML từ FE
  ```json
  {
    "name": "Quy trình Mua sắm",
    "xml": "<?xml version=\"1.0\"...>"
  }
  ```
- `GET /api/v1/processes/definitions`: Danh sách các quy trình mới nhất.
- `GET /api/v1/processes/definitions/{key}/xml`: Lấy file XML nguyên bản.

### 2. Process Instances
- `POST /api/v1/process-instances/start`: Bắt đầu quy trình
  ```json
  {
    "processDefinitionKey": "Process_LeaveRequest",
    "businessKey": "LEAVE-2026-001",
    "variables": {
      "employeeName": "Nguyễn Văn A",
      "leaveDays": 3
    }
  }
  ```
- `GET /api/v1/process-instances`: Danh sách Instance active.
- `GET /api/v1/process-instances/{instanceId}/variables`: Biến của instance.

### 3. User Tasks
- `GET /api/v1/tasks?assignee=manager1`: Lấy nhiệm vụ theo người xử lý.
- `POST /api/v1/tasks/{taskId}/complete`: Hoàn thành bước duyệt
  ```json
  {
    "userId": "manager1",
    "variables": {
      "approved": true,
      "comment": "Đồng ý cấp phép nghỉ"
    }
  }
  ```


/api/processes
/api/processes/{id}
/api/tasks
/api/tasks/{taskId}/approve
/api/tasks/{taskId}/reject
/api/tasks/{taskId}/delegate
/api/ai/summarize
/api/forms
/api/forms/{id}
/api/departments
/api/departments/{id}
/api/employees
/api/employees/{id}
/api/employees/{id}/register-account
/api/employees/bulk-import
/api/employees/{employeeId}/assign-account
/api/accounts
/api/regulations
/api/regulations/{id}
/api/regulations/{regId}/start-process
/api/process-instances
/api/sla-ola/violations
/api/auto-delegate-rules
/api/auto-delegate-rules/{id}
/api/auth/login
/api/auth/register
/api/notifications
/api/notifications/read-all
/api/notifications/clear-all
