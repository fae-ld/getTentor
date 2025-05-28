package com.atomic.getTentor.dto;

public class UserProfileDTO {
    private String nim;
    private String nama;
    private String email;
    private String fotoUrl;

    public UserProfileDTO(String nim, String nama, String email, String fotoUrl) {
        this.setNim(nim);
        this.setNama(nama);
        this.setEmail(email);
        this.setFotoUrl(fotoUrl);
    }
    // Getters and Setters
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return this.nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return this.email; }
    public void setEmail(String nama) { this.email = email; }

    public String getFotoUrl() { return this.fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

}

