package com.atomic.getTentor.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Tentor;

public class MenteeDTO {
    private Integer id;
    private MahasiswaDTO mahasiswa;
    private String fotoUrl;
    private List<TentorDTO> tentorFavorite;

    public MenteeDTO(Mentee mentee) {
        this.id = mentee.getId();
        this.mahasiswa = new MahasiswaDTO(mentee.getMahasiswa());
        this.fotoUrl = mentee.getFotoUrl();
        this.tentorFavorite = mentee.getTentorFavorite()
                .stream()
                .map(TentorDTO::new)
                .collect(Collectors.toList());
    }

    // Getters
    public Integer getId() { return id; }
    public MahasiswaDTO getMahasiswa() { return mahasiswa; }
    public String getFotoUrl() { return fotoUrl; }
    public List<TentorDTO> getTentorFavorite() { return tentorFavorite; }
}