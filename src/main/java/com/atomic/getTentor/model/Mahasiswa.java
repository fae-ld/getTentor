package com.atomic.getTentor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mahasiswa")
public class Mahasiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;
    private String jurusan;

    public Mahasiswa() {
    }

    public Mahasiswa(Long id, String nama, String jurusan) {
        this.id = id;
        this.nama = nama;
        this.jurusan = jurusan;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
}
