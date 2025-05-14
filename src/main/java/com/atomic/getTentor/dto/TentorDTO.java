package com.atomic.getTentor.dto;

import java.util.List;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Tentor;

public class TentorDTO {
    private Integer id;
    private String nim;
    private String nama;
    private String email;
    private Double ipk;
    private List<String> pengalaman;
    private String password;

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
    public String getPassword() { return password; }

    public void setId(Integer id) { this.id = id; }
    public void setNim(String nim) { this.nim = nim; }
    public void setNama(String nama) { this.nama = nama; }
    public void setEmail(String email) { this.email = email; }
    public void setIpk(Double ipk) { this.ipk = ipk; }
    public void setPengalaman(List<String> pengalaman) { this.pengalaman = pengalaman; }
    public void setPassword(String password) { this.password = password; }
}
