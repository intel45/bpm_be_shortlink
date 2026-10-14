package com.company.bpm.service;

import com.example.congvan.modal.Congvan;
import com.example.congvan.repository.CongvanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CongvanService {
    private final CongvanRepository repository;

    public CongvanService(CongvanRepository repository) {
        this.repository = repository;
    }

    public Congvan create(Congvan congvan) {
        return repository.save(congvan);
    }

    public List<Congvan> findAll() {
        return repository.findAll();
    }

    public Optional<Congvan> findById(Long id) {
        return repository.findById(id);
    }

    public Congvan phanCong(Long id, String trangThai) {
        Congvan cv = repository.findById(id).orElseThrow();
        cv.setTrangThai(trangThai);
        return repository.save(cv);
    }

    public Congvan hoanThanh(Long id) {
        Congvan cv = repository.findById(id).orElseThrow();
        cv.setTrangThai("HOAN_THANH");
        return repository.save(cv);
    }

    public void markSigned(Long id) {
    }
}
