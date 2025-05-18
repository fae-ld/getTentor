package com.atomic.getTentor.controller;

import com.atomic.getTentor.dto.MenteeDTO;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.security.JwtService;
import com.atomic.getTentor.service.MenteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.atomic.getTentor.repository.MenteeRepository;
import com.atomic.getTentor.repository.TentorRepository;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api/mentees")
public class MenteeController {

    @Autowired
    private MenteeRepository MenteeRepository;

    @Autowired
    private TentorRepository TentorRepository;

    @Autowired
    private MenteeService menteeService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<MenteeDTO>> getAllMentees() {
        return ResponseEntity.ok(menteeService.getAllMentees());
    }

    @PostMapping("/{menteeId}/favorites/{tentorId}")
    public ResponseEntity<String> addFavoriteTentor(@PathVariable Integer menteeId,@PathVariable Integer tentorId) {
        Optional<Mentee> menteeOpt = MenteeRepository.findById(menteeId);
        Optional<Tentor> tentorOpt = TentorRepository.findById(tentorId);
        if (menteeOpt.isEmpty() || tentorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mentee atau Tentor tidak ditemukan");
        }
        Mentee mentee = menteeOpt.get();
        Tentor tentor = tentorOpt.get();
        mentee.getTentorFavorite().add(tentor);  // make sure it's a Set or List
        MenteeRepository.save(mentee);
        return ResponseEntity.ok("Tentor berhasil ditambahkan ke favorit");
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Mentee mentee) {
//
//    }
}
