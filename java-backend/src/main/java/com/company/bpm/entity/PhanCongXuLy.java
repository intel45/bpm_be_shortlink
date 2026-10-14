package com.company.bpm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

 @Entity
 @Data
 public class PhanCongXuLy {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private Long congVanId;
     private String nguoiNhan;
     private String vaiTro;
     private LocalDate hanXuLy;
 }
