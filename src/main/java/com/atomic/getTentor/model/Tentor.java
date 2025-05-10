package com.atomic.getTentor.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<String> getPengalamanList() {
        if(this.pengalaman == null || this.pengalaman.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.pengalaman.split("\\|"));
    }
}
