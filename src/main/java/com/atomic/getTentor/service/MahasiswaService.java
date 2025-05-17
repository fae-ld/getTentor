package com.atomic.getTentor.service;

import com.atomic.getTentor.dto.MahasiswaDTO;
import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MahasiswaService {
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public List<MahasiswaDTO> getAllMahasiswa() {
        List<Mahasiswa> entities = mahasiswaRepository.findAll();
        return entities.stream()
                .map(MahasiswaDTO::new)
                .collect(Collectors.toList());
    }

    public List<Mahasiswa> findMahasiswaByNama(String nama) {
        return mahasiswaRepository.findByNamaContaining(nama);
    }

    public MahasiswaDTO getByNim(String nim) {
        Mahasiswa mhs = mahasiswaRepository.findById(nim)
                .orElseThrow(() -> new RuntimeException("Mahasiswa tidak ditemukan"));
        return new MahasiswaDTO(mhs);
    }
}
