package com.atomic.getTentor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.TentorRepository;

@Service
public class TentorService {
    @Autowired
    private TentorRepository tentorRepository;

    public List<TentorDTO> getAllTentors() {
        List<Tentor> tentors = tentorRepository.findByMahasiswaIsNotNull();
        return tentors.stream()
                .map(TentorDTO::new)
                .collect(Collectors.toList());
    }

    public Tentor findByEmail(String email) {
        return tentorRepository.findByEmail(email);
    }

    public void login(String email, String password) {
        Tentor tentor = tentorRepository.findByEmail(email);
        if (tentor == null || !BCrypt.checkpw(password, tentor.getPassword())) {
            throw new RuntimeException("Invalid email or password");
            }   
    }
    

}
