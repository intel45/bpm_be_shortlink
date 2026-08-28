package com.company.bpm.controller;

import com.company.bpm.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring Boot REST Controller quản lý Phòng Ban, Nhân Viên và Tài Khoản hệ thống BPM.
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class DepartmentEmployeeController {

    private final Map<String, DepartmentDto> departmentsStore = new ConcurrentHashMap<>();
    private final Map<String, EmployeeDto> employeesStore = new ConcurrentHashMap<>();
    private final Map<String, UserAccountDto> accountsStore = new ConcurrentHashMap<>();

    public DepartmentEmployeeController() {
        // Init sample Departments
        DepartmentDto d1 = new DepartmentDto();
        d1.setId("dept-001");
        d1.setCode("DEPT_IT");
        d1.setName("Phòng Công Nghệ Thông Tin & Hạ Tầng");
        d1.setDescription("Quản lý hệ thống máy chủ, ứng dụng phần mềm, an ninh mạng và thiết bị IT.");
        d1.setManagerName("Nguyễn Văn An");
        d1.setEmployeeCount(12);
        d1.setCreatedAt("2026-01-10");
        departmentsStore.put(d1.getId(), d1);

        DepartmentDto d2 = new DepartmentDto();
        d2.setId("dept-002");
        d2.setCode("DEPT_HR");
        d2.setName("Phòng Nhân Sự & Hành Chính");
        d2.setDescription("Quản lý tuyển dụng, đào tạo, chế độ chính sách, bảo hiểm và vận hành văn phòng.");
        d2.setManagerName("Trần Thị Mai");
        d2.setEmployeeCount(8);
        d2.setCreatedAt("2026-01-10");
        departmentsStore.put(d2.getId(), d2);

        DepartmentDto d3 = new DepartmentDto();
        d3.setId("dept-003");
        d3.setCode("DEPT_FINANCE");
        d3.setName("Khối Tài Chính & Kế Toán");
        d3.setDescription("Kế toán quản trị, lập kế hoạch ngân sách, giải ngân mua sắm và báo cáo tài chính.");
        d3.setManagerName("Phạm Đức CFO");
        d3.setEmployeeCount(6);
        d3.setCreatedAt("2026-01-12");
        departmentsStore.put(d3.getId(), d3);

        // Init sample Employees
        EmployeeDto e1 = new EmployeeDto();
        e1.setId("emp-101");
        e1.setCode("NV-001");
        e1.setFullName("Nguyễn Văn An");
        e1.setEmail("an.nguyen@company.com");
        e1.setPhone("0901234567");
        e1.setPosition("Trưởng Phòng CNTT");
        e1.setDepartmentId("dept-001");
        e1.setDepartmentName("Phòng Công Nghệ Thông Tin & Hạ Tầng");
        e1.setHasAccount(true);
        e1.setUsername("an_nguyen");
        e1.setCreatedAt("2026-01-10");
        employeesStore.put(e1.getId(), e1);

        EmployeeDto e2 = new EmployeeDto();
        e2.setId("emp-102");
        e2.setCode("NV-002");
        e2.setFullName("Trần Thị Mai");
        e2.setEmail("mai.tran@company.com");
        e2.setPhone("0912345678");
        e2.setPosition("Trưởng Phòng Nhân Sự");
        e2.setDepartmentId("dept-002");
        e2.setDepartmentName("Phòng Nhân Sự & Hành Chính");
        e2.setHasAccount(true);
        e2.setUsername("mai_tran");
        e2.setCreatedAt("2026-01-10");
        employeesStore.put(e2.getId(), e2);

        // Init sample Accounts
        UserAccountDto a1 = new UserAccountDto();
        a1.setId("acc-1");
        a1.setEmployeeId("emp-101");
        a1.setEmployeeName("Nguyễn Văn An");
        a1.setUsername("an_nguyen");
        a1.setEmail("an.nguyen@company.com");
        a1.setRole("Admin");
        a1.setStatus("active");
        a1.setCreatedAt("2026-01-10");
        accountsStore.put(a1.getId(), a1);

        UserAccountDto a2 = new UserAccountDto();
        a2.setId("acc-2");
        a2.setEmployeeId("emp-102");
        a2.setEmployeeName("Trần Thị Mai");
        a2.setUsername("mai_tran");
        a2.setEmail("mai.tran@company.com");
        a2.setRole("Manager");
        a2.setStatus("active");
        a2.setCreatedAt("2026-01-10");
        accountsStore.put(a2.getId(), a2);
    }

    // --- DEPARTMENTS API ---

    @GetMapping("/departments")
    public ResponseEntity<Map<String, Object>> getDepartments() {
        return ResponseEntity.ok(Map.of("success", true, "data", new ArrayList<>(departmentsStore.values())));
    }

    @PostMapping("/departments")
    public ResponseEntity<Map<String, Object>> createDepartment(@RequestBody DepartmentDto dept) {
        if (dept.getId() == null || dept.getId().isEmpty()) {
            dept.setId("dept-" + System.currentTimeMillis());
        }
        dept.setCreatedAt(LocalDate.now().toString());
        dept.setEmployeeCount(0);
        departmentsStore.put(dept.getId(), dept);
        return ResponseEntity.ok(Map.of("success", true, "data", dept));
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<Map<String, Object>> updateDepartment(@PathVariable String id, @RequestBody DepartmentDto dept) {
        DepartmentDto existing = departmentsStore.get(id);
        if (existing == null) {
            dept.setId(id);
            dept.setCreatedAt(LocalDate.now().toString());
            departmentsStore.put(id, dept);
            return ResponseEntity.ok(Map.of("success", true, "data", dept));
        }
        if (dept.getCode() != null) existing.setCode(dept.getCode());
        if (dept.getName() != null) existing.setName(dept.getName());
        if (dept.getDescription() != null) existing.setDescription(dept.getDescription());
        if (dept.getManagerName() != null) existing.setManagerName(dept.getManagerName());
        return ResponseEntity.ok(Map.of("success", true, "data", existing));
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Map<String, Object>> deleteDepartment(@PathVariable String id) {
        departmentsStore.remove(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Đã xoá phòng ban " + id));
    }

    // --- EMPLOYEES API ---

    @GetMapping("/employees")
    public ResponseEntity<Map<String, Object>> getEmployees() {
        return ResponseEntity.ok(Map.of("success", true, "data", new ArrayList<>(employeesStore.values())));
    }

    @PostMapping("/employees")
    public ResponseEntity<Map<String, Object>> createEmployee(@RequestBody EmployeeDto emp) {
        if (emp.getId() == null || emp.getId().isEmpty()) {
            emp.setId("emp-" + System.currentTimeMillis());
        }
        emp.setCreatedAt(LocalDate.now().toString());
        if (emp.getDepartmentId() != null && departmentsStore.containsKey(emp.getDepartmentId())) {
            emp.setDepartmentName(departmentsStore.get(emp.getDepartmentId()).getName());
        }
        employeesStore.put(emp.getId(), emp);
        return ResponseEntity.ok(Map.of("success", true, "data", emp));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployee(@PathVariable String id, @RequestBody EmployeeDto emp) {
        EmployeeDto existing = employeesStore.get(id);
        if (existing == null) {
            emp.setId(id);
            emp.setCreatedAt(LocalDate.now().toString());
            employeesStore.put(id, emp);
            return ResponseEntity.ok(Map.of("success", true, "data", emp));
        }
        if (emp.getDepartmentId() != null && departmentsStore.containsKey(emp.getDepartmentId())) {
            existing.setDepartmentId(emp.getDepartmentId());
            existing.setDepartmentName(departmentsStore.get(emp.getDepartmentId()).getName());
        }
        if (emp.getFullName() != null) existing.setFullName(emp.getFullName());
        if (emp.getEmail() != null) existing.setEmail(emp.getEmail());
        if (emp.getPhone() != null) existing.setPhone(emp.getPhone());
        if (emp.getPosition() != null) existing.setPosition(emp.getPosition());
        return ResponseEntity.ok(Map.of("success", true, "data", existing));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployee(@PathVariable String id) {
        employeesStore.remove(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Đã xoá nhân viên " + id));
    }

    @PostMapping("/employees/bulk-import")
    public ResponseEntity<Map<String, Object>> bulkImportEmployees(@RequestBody BulkImportEmployeesRequestDto request) {
        List<EmployeeDto> imported = new ArrayList<>();
        if (request.getEmployees() != null) {
            for (EmployeeDto emp : request.getEmployees()) {
                emp.setId("emp-" + System.currentTimeMillis() + "-" + (int)(Math.random()*1000));
                emp.setCreatedAt(LocalDate.now().toString());
                employeesStore.put(emp.getId(), emp);
                imported.add(emp);
            }
        }
        return ResponseEntity.ok(Map.of("success", true, "data", imported));
    }

    @PostMapping("/employees/{id}/assign-account")
    public ResponseEntity<Map<String, Object>> assignAccount(@PathVariable String id, @RequestBody AssignAccountRequestDto request) {
        EmployeeDto emp = employeesStore.get(id);
        if (emp == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Không tìm thấy nhân viên"));
        }
        emp.setHasAccount(true);
        emp.setUsername(request.getUsername());

        UserAccountDto acc = new UserAccountDto();
        acc.setId("acc-" + System.currentTimeMillis());
        acc.setEmployeeId(id);
        acc.setEmployeeName(emp.getFullName());
        acc.setUsername(request.getUsername());
        acc.setEmail(emp.getEmail());
        acc.setRole(request.getRole() != null ? request.getRole() : "Employee");
        acc.setStatus("active");
        acc.setCreatedAt(LocalDate.now().toString());
        accountsStore.put(acc.getId(), acc);

        return ResponseEntity.ok(Map.of("success", true, "data", emp, "account", acc));
    }

    @PostMapping("/employees/{id}/register-account")
    public ResponseEntity<Map<String, Object>> registerAccount(@PathVariable String id, @RequestBody AssignAccountRequestDto request) {
        return assignAccount(id, request);
    }

    // --- ACCOUNTS API ---

    @GetMapping("/accounts")
    public ResponseEntity<Map<String, Object>> getAccounts() {
        return ResponseEntity.ok(Map.of("success", true, "data", new ArrayList<>(accountsStore.values())));
    }
}
