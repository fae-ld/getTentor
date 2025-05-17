package com.atomic.getTentor.controller;

import com.atomic.getTentor.dto.MenteeDTO;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.security.JwtService;
import com.atomic.getTentor.service.MenteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentees")
public class MenteeController {
    @Autowired
    private MenteeService menteeService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<MenteeDTO>> getAllMentees() {
        return ResponseEntity.ok(menteeService.getAllMentees());
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Mentee mentee) {
//
//    }
}
