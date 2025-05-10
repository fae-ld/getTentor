package com.atomic.getTentor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "matakuliah")
public class MataKuliah {
    @Id
    
    @Column(length = 12, columnDefinition = "VARCHAR(12)")
    private String nama;

    public MataKuliah(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
