package com.atomic.getTentor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.model.Admin;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.model.VerificationStatus;
import com.atomic.getTentor.repository.AdminRepository;
import com.atomic.getTentor.repository.TentorRepository;

@Service
public class AdminService {


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TentorRepository tentorRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void login(String email, String password) {
        Admin admin = adminRepository.findByMahasiswaEmail(email);
        if (admin == null || !passwordEncoder.matches(password, admin.getMahasiswa().getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public Tentor ubahStatus(Integer tentorId,String status){
        Tentor tentor = tentorRepository.findById(tentorId).orElseThrow(()->new RuntimeException("Tentor not found"));

        try{
            VerificationStatus newStatus = VerificationStatus.valueOf(status);
            tentor.setVerificationStatus(newStatus);
            Tentor save = tentorRepository.save(tentor);
            return save;
            
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status tidak valid. Gunakan: PENDING, VERIFIED, REJECTED, SUSPENDED");
        }

    }

    public List<TentorDTO> searchTentors(String q) {
        if (q == null || q.isBlank()) {
            return tentorRepository.findAll().stream()
                    .map(TentorDTO::new)
                    .toList();
        }

        return tentorRepository.searchTentorByKeywordAdmin(q).stream()
                .map(TentorDTO::new)
                .toList();
    }


}
