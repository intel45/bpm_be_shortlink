package com.company.bpm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

 @Entity
 @Data
 public class NhatKyHeThong {
     @Id
     private Long id;

     private String nguoiThucHien;
     private String hanhDong;
     private String doiTuong;
     private Long doiTuongId;
     private LocalDateTime thoiGian;
 }
