package com.atomic.getTentor.dto;

import com.atomic.getTentor.model.MataKuliah;

public class MataKuliahDTO {
    private Integer id;
    private String nama;

    public MataKuliahDTO() {}

    public MataKuliahDTO(MataKuliah mk) {
        this.id = mk.getId();
        this.nama = mk.getNama();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
}