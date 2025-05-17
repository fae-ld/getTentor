package com.atomic.getTentor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atomic.getTentor.dto.MenteeDTO;
import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.repository.MahasiswaRepository;
import com.atomic.getTentor.repository.MenteeRepository;

@Service
public class MenteeService {
    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    public List<MenteeDTO> getAllMentees() {
        List<Mentee> mentees = menteeRepository.findByMahasiswaIsNotNull();
        return mentees.stream()
                .map(MenteeDTO::new)
                .collect(Collectors.toList());
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public void login(String email, String password) {
        Mentee mentee = menteeRepository.findByMahasiswaEmail(email);
        if (mentee == null || !passwordEncoder.matches(password, mentee.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public void register(MenteeDTO menteeDTO) {
        // 1. Cek apakah email sudah ada
        if (mahasiswaRepository.existsByEmail(menteeDTO.getEmail())) {
            throw new RuntimeException("Email sudah digunakan!");
        }

        // 2. Membuat objek Mahasiswa
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNim(menteeDTO.getNim());
        mahasiswa.setNama(menteeDTO.getNama());
        mahasiswa.setEmail(menteeDTO.getEmail());
        mahasiswa.setPassword(passwordEncoder.encode(menteeDTO.getPassword())); // Hash password

        // 3. Menyimpan Mahasiswa ke database
        mahasiswaRepository.save(mahasiswa);

        // 4. Membuat objek Mentee
        Mentee mentee = new Mentee();
        mentee.setMahasiswa(mahasiswa);

        // 5. Menyimpan Mentee ke database
        menteeRepository.save(mentee);
    }
}
