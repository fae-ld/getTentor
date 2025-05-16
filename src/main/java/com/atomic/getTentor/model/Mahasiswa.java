package com.atomic.getTentor.model;
import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
@Inheritance(strategy = InheritanceType.JOINED)
public class Mahasiswa implements Account {

    @Id
    @Column(length = 12, columnDefinition = "CHAR(12)")
    private String nim;

    @Column(length = 255, columnDefinition = "VARCHAR(255)")
    private String nama;

    @Column(length = 255, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(length = 255, columnDefinition = "VARCHAR(255)")
    private String password;

    

    public Mahasiswa() {}

    public Mahasiswa(String nim, String nama, String email, String password) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
        this.password = password;
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

    @Override
    public void login() {}
    @Override
    public void register() {}
    @Override
    public void logout() {}
    @Override
    public void updateProfile() {}
}
