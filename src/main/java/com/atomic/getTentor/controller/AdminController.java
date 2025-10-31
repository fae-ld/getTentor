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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atomic.getTentor.dto.AdminDTO;
import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.model.Admin;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.AdminRepository;
import com.atomic.getTentor.repository.TentorRepository;
import com.atomic.getTentor.service.AdminService;
import com.atomic.getTentor.service.TentorService;
import com.atomic.getTentor.security.JwtService;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TentorRepository tentorRepository;


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AdminDTO adminDTO) {
        try {
            adminService.login(adminDTO.getEmail(), adminDTO.getPassword());
            Admin mentee = adminRepository.findByMahasiswaEmail(adminDTO.getEmail());
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


    @GetMapping
    public ResponseEntity<List<TentorDTO>> searchAdmin(@RequestParam(required = false) String q) {
        return ResponseEntity.ok(adminService.searchTentors(q));
    }

    @GetMapping("/tentor/{id}")
    public ResponseEntity<TentorDTO> getTentorById(@PathVariable Integer id) {
        Optional<Tentor> tentorOpt = tentorRepository.findWithMataKuliahById(id);

        return tentorOpt
                .map(tentor -> ResponseEntity.ok(new TentorDTO(tentor)))
                .orElse(ResponseEntity.notFound().build());
    }



    





}
