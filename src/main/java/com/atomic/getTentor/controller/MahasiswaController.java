package com.atomic.getTentor.controller;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.service.MahasiswaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mahasiswa")
public class MahasiswaController {

    private final MahasiswaService service;

    public MahasiswaController(MahasiswaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Mahasiswa> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mahasiswa> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mahasiswa create(@RequestBody Mahasiswa mahasiswa) {
        return service.save(mahasiswa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
