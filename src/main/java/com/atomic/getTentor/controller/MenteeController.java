package com.atomic.getTentor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atomic.getTentor.dto.MenteeDTO;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.MenteeRepository;
import com.atomic.getTentor.repository.TentorRepository;
import com.atomic.getTentor.security.JwtService;
import com.atomic.getTentor.service.MenteeService;

@RestController
@RequestMapping("/api/mentees")
public class MenteeController {

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private TentorRepository tentorRepository;

    @Autowired
    private MenteeService menteeService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<MenteeDTO>> getAllMentees() {
        return ResponseEntity.ok(menteeService.getAllMentees());
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MenteeDTO menteeDTO) {
        try {
            menteeService.login(menteeDTO.getEmail(), menteeDTO.getPassword());
            Mentee mentee = menteeRepository.findByMahasiswaEmail(menteeDTO.getEmail());
            String token = jwtService.generateToken(mentee);

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
    public ResponseEntity<Map<String, Object>> register(@RequestBody MenteeDTO menteeDTO) {
        try {
            menteeService.register(menteeDTO);
            Mentee mentee = menteeRepository.findByMahasiswaEmail(menteeDTO.getEmail());
            String token = jwtService.generateToken(mentee);

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
}
