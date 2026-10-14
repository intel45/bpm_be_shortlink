package com.company.bpm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

 @Entity
 @Data
 public class CongvanDen {
     @Id
     private Long id; // FK to Congvan

     private String soDen;
     private LocalDate ngayDen;
     private String noiGui;
     private String nguoiKy;
     private LocalDate hanXuLy;
 }
