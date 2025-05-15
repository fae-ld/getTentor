package com.atomic.getTentor.dto;

import java.util.List;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Tentor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TentorDTO {
    private Integer id;
    private String nim;
    private String nama;
    private String email;
    private Double ipk;
    private List<String> pengalaman;
    private String password;

    public TentorDTO() {}

    public TentorDTO(Tentor tentor) {
        if (tentor == null) {
            throw new IllegalArgumentException("Tentor tidak boleh null");
        }

        Mahasiswa mahasiswa = tentor.getMahasiswa();
        if (mahasiswa == null) {
            throw new IllegalArgumentException("Tentor tidak memiliki data Mahasiswa");
        }

        this.id = tentor.getId();
        this.nim = mahasiswa.getNim();
        this.nama = mahasiswa.getNama();
        this.setPassword(mahasiswa.getPassword());
        this.email = mahasiswa.getEmail();
        this.ipk = tentor.getIpk();
        this.pengalaman = tentor.getPengalamanList();
    }

    public Integer getId() { return id; }
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public Double getIpk() { return ipk; }
    public List<String> getPengalaman() { return pengalaman; }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setId(Integer id) { this.id = id; }
    public void setNim(String nim) { this.nim = nim; }
    public void setNama(String nama) { this.nama = nama; }
    public void setIpk(Double ipk) { this.ipk = ipk; }
    public void setPengalaman(List<String> pengalaman) { this.pengalaman = pengalaman; }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email tidak boleh kosong");
        }
        this.email = email;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong");
        }
        this.password = password;
    }
}
