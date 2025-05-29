package com.atomic.getTentor.dto;

public class UserProfileDTO {
    private String nim;
    private String nama;
    private String email;
    private String fotoUrl;
    private String noTelp;

    public UserProfileDTO(String nim, String nama, String email, String fotoUrl, String noTelp) {
        this.setNim(nim);
        this.setNama(nama);
        this.setEmail(email);
        this.setFotoUrl(fotoUrl);
        this.setNoTelp(noTelp);
    }
    // Getters and Setters
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return this.nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }

    public String getFotoUrl() { return this.fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public String getNoTelp() { return this.noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
}

