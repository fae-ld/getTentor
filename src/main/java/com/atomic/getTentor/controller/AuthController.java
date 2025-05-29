package com.atomic.getTentor.controller;

import com.atomic.getTentor.dto.UserProfileDTO;
import com.atomic.getTentor.model.AbstractMahasiswa;
import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.MenteeRepository;
import com.atomic.getTentor.repository.TentorRepository;
import com.atomic.getTentor.security.JwtService;
import com.atomic.getTentor.service.MenteeService;
import com.atomic.getTentor.service.TentorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TentorRepository tentorRepository;

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/me")
    public UserProfileDTO getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token tidak ditemukan");
        }

        String token = authHeader.substring(7);
        String email = jwtService.getEmailFromToken(token);

        if(jwtService.getRoleFromToken(token).equals("tentor")) {
            Tentor tentor = tentorRepository.findByMahasiswaEmail(email);
            Mahasiswa mahasiswa = tentor.getMahasiswa();
            return new UserProfileDTO(
                    mahasiswa.getNim(),
                    tentor.getNama(),
                    mahasiswa.getEmail(),
                    mahasiswa.getFotoUrl(),
                    mahasiswa.getNoTelp()
            );
        }else {
            Mentee mentee = menteeRepository.findByMahasiswaEmail(email);
            Mahasiswa mahasiswa = mentee.getMahasiswa();
            return new UserProfileDTO(
                    mahasiswa.getNim(),
                    mentee.getNama(),
                    mahasiswa.getEmail(),
                    mahasiswa.getFotoUrl(),
                    mahasiswa.getNoTelp()
            );
        }
    }
}