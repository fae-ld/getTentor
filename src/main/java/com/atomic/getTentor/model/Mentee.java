package com.atomic.getTentor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "mentee")

public class Mentee extends Mahasiswa {

    
    @ManyToMany
    @JoinTable(
        name = "Favorite",
        joinColumns = @JoinColumn(name = "mentee_id"),
        inverseJoinColumns = @JoinColumn(name = "tentor_id")
    )
    private List<Tentor> tentorFavorite = new ArrayList<Tentor>();;

    @Column(name="foto_url",length = 512)
    private String fotoUrl;

    public Mentee() {
        super();
    }

    public Mentee(String nim, String nama, String email, String password) {
        super(nim, nama, email, password);
    }

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

    @Override
    public void login() {
        // implementation not yet;
    }

    @Override
    public void register() {
        // implementation not yet;
    }

    @Override
    public void logout() {
        // implementation not yet;
    }

    @Override
    public void updateProfile() {
        // implementation not yet;
    }
}
