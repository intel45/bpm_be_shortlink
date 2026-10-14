package com.company.bpm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Congvan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String maCongVan;
    private String loaiCongVan;
    private String trichYeu;
    private LocalDate ngayBanHanh;
    private String doKhan;
    private String doMat;
    private String trangThai;
    private String nguoiTao;
    private Long phongBanId;
    private String processInstanceId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
