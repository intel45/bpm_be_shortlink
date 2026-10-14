package com.company.bpm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

 @Entity
 @Data
 public class SoCongVan {
     @Id
     private Long id;

     private Integer nam;
     private String loai;
     private Integer soHienTai;
 }
