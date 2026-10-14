package com.company.bpm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

 @Entity
 @Data
 public class ChuKySo {
     @Id
     private Long id;

     private Long congVanId;
     private String nguoiKy;
     private LocalDateTime thoiGianKy;
     private String fileDaKy;
 }
