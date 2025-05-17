package com.atomic.getTentor.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.atomic.getTentor.model.Mentee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MenteeDTO {
    private Integer id;
    private MahasiswaDTO mahasiswa;
    private String fotoUrl;
    private List<TentorDTO> tentorFavorite;
    private String email;
    private String password;


    public MenteeDTO() {}

    public MenteeDTO(Mentee mentee) {
        if (mentee == null) {
            throw new IllegalArgumentException("Mentee tidak boleh null");
        }
        if (mentee.getMahasiswa() == null) {
            throw new IllegalArgumentException("Mentee tidak memiliki data Mahasiswa");
        }
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
    public String getEmail() { return email; }

    @JsonIgnore
    public String getPassword() {
        return password;
    }


    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl;}
    public void setTentorFavorite(List<TentorDTO> tentorFavorite) { this.tentorFavorite = tentorFavorite;}

    public void setMahasiswa(MahasiswaDTO mahasiswa) {
        if (mahasiswa == null) {
            throw new IllegalArgumentException("Mahasiswa tidak boleh null");
        }
        this.mahasiswa = mahasiswa;
    }

    @JsonProperty
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email tidak boleh kosong");
        }
        this.email = email;
    }

    @JsonProperty
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong");
        }
        this.password = password;
    }
}