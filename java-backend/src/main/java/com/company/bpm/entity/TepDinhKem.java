package com.company.bpm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

 @Entity
 @Data
 public class TepDinhKem {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private Long congVanId;
     private String tenFile;
     private String duongDan;
     private String loaiFile;
     private Long kichThuoc;
     private LocalDateTime createdAt;
 }
