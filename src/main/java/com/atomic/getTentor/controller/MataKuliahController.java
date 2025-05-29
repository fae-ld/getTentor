package com.atomic.getTentor.controller;

import com.atomic.getTentor.dto.MataKuliahDTO;
import com.atomic.getTentor.model.MataKuliah;
import com.atomic.getTentor.repository.MataKuliahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mata-kuliah")
public class MataKuliahController {

    @Autowired
    private MataKuliahRepository mataKuliahRepository;

    @GetMapping
    public List<MataKuliahDTO> getAllMataKuliah() {
        return mataKuliahRepository.findAll().stream()
                .map(MataKuliahDTO::new)
                .toList();
    }

}