package com.atomic.getTentor.controller;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.service.MahasiswaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mahasiswa")
public class MahasiswaController {
    @Autowired
    private MahasiswaService mahasiswaService;

    @GetMapping("/search")
    public ResponseEntity<List<Mahasiswa>> searchMahasiswa(@RequestParam String nama) {
        return ResponseEntity.ok(mahasiswaService.findMahasiswaByNama(nama));
    }
}
