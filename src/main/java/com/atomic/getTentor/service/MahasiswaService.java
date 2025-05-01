package com.atomic.getTentor.service;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.repository.MahasiswaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MahasiswaService {

    private final MahasiswaRepository repository;

    public MahasiswaService(MahasiswaRepository repository) {
        this.repository = repository;
    }

    public List<Mahasiswa> getAll() {
        return repository.findAll();
    }

    public Optional<Mahasiswa> getById(Long id) {
        return repository.findById(id);
    }

    public Mahasiswa save(Mahasiswa m) {
        return repository.save(m);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
