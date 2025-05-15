package com.atomic.getTentor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.service.TentorService;

@RestController
@RequestMapping("/api/tentors")
public class TentorController {
    @Autowired
    private TentorService tentorService;

    @GetMapping
    public ResponseEntity<List<TentorDTO>> getAllTentors() {
        return ResponseEntity.ok(tentorService.getAllTentors());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody TentorDTO tentorDTO) {
        try {
            tentorService.login(tentorDTO.getEmail(), tentorDTO.getPassword());
            return ResponseEntity.ok("Login successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody TentorDTO tentorDTO) {
        try {
            tentorService.register(tentorDTO);
            return ResponseEntity.ok("Registrasi berhasil!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}


