package com.atomic.getTentor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "matakuliah")
public class MataKuliah {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 255, columnDefinition = "VARCHAR(255)")
    private String nama;

    @ManyToMany(mappedBy = "listMataKuliah")
    private List<Tentor> listTentor = new ArrayList<>();


    public MataKuliah(Integer id, String nama) {
        this.id = id;
        this.nama = nama;
    }
    public Integer getId() {return this.id;}
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
