package com.company.bpm.controller;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/regulations")
public class RegulationController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping("/{regId}/start-process")
    public ResponseEntity<String> startProcess(@PathVariable Long regId, @RequestBody(required = false) Map<String, Object> variables) {
        runtimeService.startProcessInstanceById(regId, variables);
        return ResponseEntity.ok("Process started for regulation ID: " + regId);
    }
}
