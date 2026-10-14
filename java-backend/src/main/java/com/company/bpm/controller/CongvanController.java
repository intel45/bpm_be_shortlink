package com.company.bpm.controller;


import com.example.congvan.modal.Congvan;
import com.example.congvan.service.CongvanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cong-van")
public class CongvanController {
    private final CongvanService service;

    public CongvanController(CongvanService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Congvan> create(@RequestBody Congvan congvan) {
        return ResponseEntity.ok(service.create(congvan));
    }

    @GetMapping
    public ResponseEntity<List<Congvan>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Congvan> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/phan-cong")
    public ResponseEntity<Congvan> phanCong(@PathVariable Long id, @RequestParam String trangThai) {
        return ResponseEntity.ok(service.phanCong(id, trangThai));
    }

    @PutMapping("/{id}/hoan-thanh")
    public ResponseEntity<Congvan> hoanThanh(@PathVariable Long id) {
        return ResponseEntity.ok(service.hoanThanh(id));
    }

    @PostMapping("/sign/{id}")
    public void sign(@PathVariable Long id){
        // gọi dịch vụ ký (VNPT/Viettel CA)
        service.markSigned(id);
    }

}
