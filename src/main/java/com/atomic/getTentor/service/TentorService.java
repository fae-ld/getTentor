package com.atomic.getTentor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.MahasiswaRepository;
import com.atomic.getTentor.repository.TentorRepository;

@Service
public class TentorService {
    @Autowired
    private TentorRepository tentorRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public List<TentorDTO> getAllTentors() {
        List<Tentor> tentors = tentorRepository.findByMahasiswaIsNotNull();
        return tentors.stream()
                .map(TentorDTO::new)
                .collect(Collectors.toList());
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void login(String email, String password) {
        Tentor tentor = tentorRepository.findByMahasiswaEmail(email);
        if (tentor == null || !passwordEncoder.matches(password, tentor.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public void register(TentorDTO tentorDTO) {
        // 1. Cek email ada ga
        if (mahasiswaRepository.existsByEmail(tentorDTO.getEmail())) {
            throw new RuntimeException("Email sudah digunakan!");
        }

        // 2. Buat objek Mahasiswa
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNim(tentorDTO.getNim());
        mahasiswa.setNama(tentorDTO.getNama());
        mahasiswa.setEmail(tentorDTO.getEmail());
        mahasiswa.setPassword(passwordEncoder.encode(tentorDTO.getPassword())); // Hash password

        // 3. Simpen Mahasiswa ke database
        mahasiswaRepository.save(mahasiswa);

        // 4. Buat objek Tentor
        Tentor tentor = new Tentor();
        tentor.setMahasiswa(mahasiswa);
        tentor.setIpk(tentorDTO.getIpk());

        // Gabungin List<String> pengalaman jadi String with pemisah "|"
        String pengalaman = tentorDTO.getPengalaman() != null
                ? String.join("|", tentorDTO.getPengalaman())
                : "";
        tentor.setPengalaman(pengalaman);

        // 5. Simpen Tentor ke database
        tentorRepository.save(tentor);
    }
}
