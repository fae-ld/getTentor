package com.atomic.getTentor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "tipe_mahasiswa")
public abstract class Mahasiswa implements Account {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 12, columnDefinition = "CHAR(12)")
    private String nim;

    @Column(length = 255, columnDefinition = "VARCHAR(255)")
    private String nama;

    @Column(length = 255, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(length = 255, columnDefinition = "VARCHAR(255)")
    private String password;

    public Mahasiswa() {
    }

    public Mahasiswa(String nim, String nama, String email, String password) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public String getNim() {return this.nim;}
    public String getNama() {return this.nama;}
    public String getEmail() {return this.email;}
    public String getPassword() {return this.password;}

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void login();
    public abstract void register();
    public abstract void logout();
    public abstract void updateProfile();
}
