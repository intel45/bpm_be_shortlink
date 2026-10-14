package com.company.bpm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

 @Entity
 @Data
 public class CongvanDi {
     @Id
     private Long id; // FK to Congvan

     private String soDi;
     private String noiNhan;
     private String nguoiKy;
     private LocalDate ngayPhatHanh;
 }
