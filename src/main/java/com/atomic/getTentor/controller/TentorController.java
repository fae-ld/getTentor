package com.atomic.getTentor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.TentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.security.JwtService;
import com.atomic.getTentor.service.TentorService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tentors")
public class TentorController {
    @Autowired
    private TentorService tentorService;

    @Autowired
    private TentorRepository tentorRepository;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<TentorDTO>> getAllTentors() {
        return ResponseEntity.ok(tentorService.getAllTentors());
    }

    @GetMapping("/search")
    public ResponseEntity<List<TentorDTO>> search(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(tentorService.searchTentors(q));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody TentorDTO tentorDTO) {
        try {
            tentorService.login(tentorDTO.getEmail(), tentorDTO.getPassword());
            Tentor tentor = tentorRepository.findByMahasiswaEmail(tentorDTO.getEmail());
            String token = jwtService.generateToken(tentor);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login berhasil");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody TentorDTO tentorDTO) {
        try {
            tentorService.register(tentorDTO);
            Tentor tentor = tentorRepository.findByMahasiswaEmail(tentorDTO.getEmail());
            String token = jwtService.generateToken(tentor);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Registrasi berhasil");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestPart(value = "data", required = false) TentorDTO tentorDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String email = jwtService.getEmailFromToken(token);

            tentorService.updateTentorProfile(email, tentorDTO, file);

            return ResponseEntity.ok(Map.of("message", "Profil berhasil diperbarui"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

}


