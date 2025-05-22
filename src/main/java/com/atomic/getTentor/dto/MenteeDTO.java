package com.atomic.getTentor.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Mentee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MenteeDTO {
    private Integer id;
    private String nim;
    private String nama;
    private String fotoUrl;
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

        Mahasiswa mahasiswa = mentee.getMahasiswa();
        this.nama = mahasiswa.getNama();
        this.nim = mahasiswa.getNim();
        this.email = mahasiswa.getEmail();
        this.setPassword(mahasiswa.getPassword());

        this.fotoUrl = mentee.getFotoUrl();
    }
    

    // Getters
    public Integer getId() { return id; }
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public String getFotoUrl() { return fotoUrl; }
    public String getEmail() { return email; }

    @JsonIgnore
    public String getPassword() {
        return password;
    }


    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl;}
    public void setNim(String nim) {this.nim = nim;}
    public void setNama(String nama) {this.nama = nama;}

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