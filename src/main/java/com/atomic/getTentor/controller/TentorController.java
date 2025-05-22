package com.atomic.getTentor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.security.JwtService;
import com.atomic.getTentor.service.TentorService;

@RestController
@RequestMapping("/api/tentors")
public class TentorController {
    @Autowired
    private TentorService tentorService;

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
    public ResponseEntity<String> login(@RequestBody TentorDTO tentorDTO) {
        try {
            // Periksa apakah login berhasil dengan email dan password
            tentorService.login(tentorDTO.getEmail(), tentorDTO.getPassword());  // Pastikan service login ini benar

            // Generate token JWT setelah login berhasil
            String token = jwtService.generateToken(tentorDTO.getEmail());

            // Response yang akan dikirim ke frontend
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login berhasil");

            return ResponseEntity.ok(response.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody TentorDTO tentorDTO) {
        try {
            tentorService.register(tentorDTO);
            String token = jwtService.generateToken(tentorDTO.getEmail());

            Map<String,Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Registrasi berhasil");
            
            return ResponseEntity.ok(response.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}


