package com.atomic.getTentor.controller;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.service.TentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tentors")
public class TentorController {
    @Autowired
    private TentorService tentorService;

    @GetMapping
    public ResponseEntity<List<TentorDTO>> getAllTentors() {
        return ResponseEntity.ok(tentorService.getAllTentors());
    }
}


