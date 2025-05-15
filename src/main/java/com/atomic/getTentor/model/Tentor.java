package com.atomic.getTentor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tentor")
public class Tentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Hubungan one-to-one ke Mahasiswa
    @OneToOne
    @JoinColumn(name = "nim", referencedColumnName = "nim", unique = true)
    private Mahasiswa mahasiswa;

    @Column(columnDefinition = "double")
    private Double ipk;

    @Column(columnDefinition = "TEXT")
    private String pengalaman;

    public Tentor() {}

    public Tentor(Mahasiswa mahasiswa, Double ipk, String pengalaman) {
        this.mahasiswa = mahasiswa;
        this.ipk = ipk;
        this.pengalaman = pengalaman;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(Mahasiswa mahasiswa) { this.mahasiswa = mahasiswa; }
    public Double getIpk() { return ipk; }
    public void setIpk(Double ipk) { this.ipk = ipk; }
    public String getPengalaman() { return pengalaman; }
    public void setPengalaman(String pengalaman) { this.pengalaman = pengalaman; }

    public String getEmail() {
        return mahasiswa != null ? mahasiswa.getEmail() : null;
    }
    public String getPassword() {
        return mahasiswa != null ? mahasiswa.getPassword() : null;
    }

    public void setPassword(String password) { this.mahasiswa.setPassword(password);}

    public List<String> getPengalamanList() {
        if(this.pengalaman == null || this.pengalaman.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.pengalaman.split("\\|"));
    }
}