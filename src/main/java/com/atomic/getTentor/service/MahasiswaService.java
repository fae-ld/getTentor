package com.atomic.getTentor.service;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MahasiswaService {

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public List<Mahasiswa> getAllMahasiswa() {
        return mahasiswaRepository.findAll();
    }
}