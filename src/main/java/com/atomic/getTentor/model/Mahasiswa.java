package com.atomic.getTentor.model;
import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
@Inheritance(strategy = InheritanceType.JOINED)
public class Mahasiswa {

    @Id
    @Column(length = 12, columnDefinition = "CHAR(12)")
    private String nim;

    @Column(length = 255, columnDefinition = "VARCHAR(255)", nullable = false)
    private String nama;

    @Column(length = 255, columnDefinition = "VARCHAR(255)", nullable = false)
    private String email;

    @Column(name="foto_url",length = 512)
    private String fotoUrl;

    @Column(name="no_telp",length = 12)
    private String noTelp;

    @Column(length = 255, columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    public Mahasiswa() {}

    public Mahasiswa(String nim, String nama, String email, String password, String fotoUrl, String noTelp) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.fotoUrl = fotoUrl;
        this.noTelp = noTelp;
    }

    // Getters & Setters
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
}
