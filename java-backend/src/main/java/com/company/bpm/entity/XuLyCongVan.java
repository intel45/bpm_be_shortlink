 package com.company.bpm.entity;

 import jakarta.persistence.Entity;
 import jakarta.persistence.GeneratedValue;
 import jakarta.persistence.GenerationType;
 import jakarta.persistence.Id;
 import lombok.Data;

 import java.time.LocalDateTime;

 @Entity
 @Data
 public class XuLyCongVan {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private Long congVanId;
     private String taskId;
     private String nguoiXuLy;
     private String hanhDong;
     private String yKien;
     private LocalDateTime thoiGian;
 }
