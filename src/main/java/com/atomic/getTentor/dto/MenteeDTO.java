package com.atomic.getTentor.dto;

import com.atomic.getTentor.model.Mentee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MenteeDTO {
    private String nim;
    private String nama;
    private String email;
    private String password;
    private String fotoUrl;

    public MenteeDTO() {}

    public MenteeDTO(Mentee mentee) {
        if (mentee == null) {
            throw new IllegalArgumentException("Mentee tidak boleh null");
        }

        this.nim = mentee.getNim();
        this.nama = mentee.getNama();
        this.email = mentee.getEmail();
        this.password = mentee.getPassword();
        this.fotoUrl = mentee.getFotoUrl();
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email tidak boleh kosong");
        }
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong");
        }
        this.password = password;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}