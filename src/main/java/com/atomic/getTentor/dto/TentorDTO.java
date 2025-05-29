package com.atomic.getTentor.dto;


import java.util.ArrayList;
import java.util.List;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.MataKuliah;
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
    private Double rataRataRating;
    private List<ReviewDTO> listReview;
    private List<MataKuliahDTO> listMataKuliah;
    private String fotoUrl;
    private String noTelp;
    private Double averageRating;
    private Integer ratingCount;

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
        this.rataRataRating = tentor.getAverageRating();
        this.listReview = tentor.getListReview() != null
            ? tentor.getListReview().stream().map(ReviewDTO::new).toList()
            : new ArrayList<>();
        this.fotoUrl = tentor.getFotoUrl();
        this.noTelp = tentor.getNoTelp();
        this.listMataKuliah = tentor.getListMataKuliah() == null ? new ArrayList<>() : tentor.getListMataKuliah().stream()
                .map(MataKuliahDTO::new)
                .toList();
        this.averageRating = tentor.getAverageRating();
        this.ratingCount = this.listReview.size();
    }

    public Integer getId() { return id; }
    public String getNim() { return nim; }
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public Double getIpk() { return ipk; }
    public List<String> getPengalaman() { return pengalaman; }
    public Double getRataRataRating() {return rataRataRating;}
    public List<ReviewDTO> getListReview() { return listReview; }
    public String getFotoUrl() { return fotoUrl; }
    public String getNoTelp() { return noTelp; }
    public List<MataKuliahDTO> getListMataKuliah() {
        return listMataKuliah;
    }
    public Double getAverageRating() { return averageRating; }
    public Integer getRatingCount() { return ratingCount; }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setId(Integer id) { this.id = id; }
    public void setNim(String nim) { this.nim = nim; }
    public void setNama(String nama) { this.nama = nama; }
    public void setIpk(Double ipk) { this.ipk = ipk; }
    public void setPengalaman(List<String> pengalaman) { this.pengalaman = pengalaman; }
    public void setRataRataRating(Double rataRataRating) {this.rataRataRating = rataRataRating;}
    public void setListReview(List<ReviewDTO> listReview) { this.listReview = listReview; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
    public void setListMataKuliah(List<MataKuliahDTO> listMataKuliah) {
        this.listMataKuliah = listMataKuliah;
    }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public void setRatingCount(Integer ratingCount) { this.ratingCount = ratingCount; }

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
