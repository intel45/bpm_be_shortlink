
package com.company.bpm.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

  @GetMapping
  public List<String> list(){
    return List.of("CV-001","CV-002");
  }

  @PostMapping
  public String create(@RequestBody Map<String,Object> body){
    return "CREATED";
  }
}
