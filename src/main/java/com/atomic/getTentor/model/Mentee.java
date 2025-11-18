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
public class Mentee extends AbstractMahasiswa {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="nim", referencedColumnName = "nim", unique = true)
    private Mahasiswa mahasiswa;

    public Mentee() {}

    public Mentee(Mahasiswa mahasiswa, String fotoUrl) {
        this.mahasiswa = mahasiswa;
    }

    @Override
    public Integer getId() { return id; }

    @Override
    public String getEmail() { return mahasiswa != null ? mahasiswa.getEmail() : null; }

    @Override
    public String getNama() { return mahasiswa != null ? mahasiswa.getNama() : null; }

    @Override
    public String getRole() { return "mentee"; }

    @Override
    public String getFotoUrl() { return mahasiswa != null ? mahasiswa.getFotoUrl() : null; }

    @Override
    public String getNoTelp() { return mahasiswa != null ? mahasiswa.getNoTelp() : null; }

    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(Mahasiswa mahasiswa) { this.mahasiswa = mahasiswa; }

    public String getPassword() { return mahasiswa != null ? mahasiswa.getPassword() : null;}

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
