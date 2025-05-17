package com.atomic.getTentor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mentee")
public class Mentee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="nim", referencedColumnName = "nim", unique = true)
    private Mahasiswa mahasiswa;

    @ManyToMany
    @JoinTable(
        name = "Favorite",
        joinColumns = @JoinColumn(name = "mentee_id"),
        inverseJoinColumns = @JoinColumn(name = "tentor_id")
    )
    private List<Tentor> tentorFavorite = new ArrayList<Tentor>();;

    @Column(name="foto_url",length = 512)
    private String fotoUrl;

    public Mentee() {}

    public Mentee(Mahasiswa mahasiswa, String fotoUrl) {
        this.mahasiswa = mahasiswa;
        this.fotoUrl = fotoUrl;
    }

    public Integer getId() { return id; }
    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(Mahasiswa mahasiswa) { this.mahasiswa = mahasiswa; }

    public String getEmail() { return mahasiswa != null ? mahasiswa.getEmail() : null; }
    public String getPassword() { return mahasiswa != null ? mahasiswa.getPassword() : null;}

    public List<Tentor> getTentorFavorite() {
        return tentorFavorite;
    }

    public void setTentorFavorite(List<Tentor> tentorFavorite) {
        this.tentorFavorite = tentorFavorite;
    }

    public void appendTentorFavorite(Tentor tentor) {
        tentorFavorite.add(tentor);
    }

    public void removeTentorFavorite(Tentor tentor) {
        tentorFavorite.remove(tentor);
    }

    public void setFotoUrl(String fotoUrl){
        this.fotoUrl=fotoUrl;
    }
    
    public String getFotoUrl(){
        return this.fotoUrl;}

    public void login() {
        // implementation not yet;
    }

    public void register() {
        // implementation not yet;
    }

    public void logout() {
        // implementation not yet;
    }

    public void updateProfile() {
        // implementation not yet;
    }
}
