package com.atomic.getTentor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "admin")
public class Admin extends AbstractMahasiswa {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="nim", referencedColumnName = "nim", unique = true)
    private Mahasiswa mahasiswa;

    public Admin(){};

    public Admin(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    @Override
    public Integer getId() { return id; }

    @Override
    public String getEmail() { return mahasiswa != null ? mahasiswa.getEmail() : null; }

    @Override
    public String getNama() { return mahasiswa != null ? mahasiswa.getNama() : null; }

    @Override
    public String getRole() { return "admin"; }

    @Override
    public String getFotoUrl() { return mahasiswa != null ? mahasiswa.getFotoUrl() : null; }

    @Override
    public String getNoTelp() { return mahasiswa != null ? mahasiswa.getNoTelp() : null; }

    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(Mahasiswa mahasiswa) { this.mahasiswa = mahasiswa; }

    public String getPassword() { return mahasiswa != null ? mahasiswa.getPassword() : null;}

    
}
