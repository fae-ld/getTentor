package com.atomic.getTentor.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

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

    public Mentee(String nim, String nama, String email, String password,String fotoUrl) {
        super(nim, nama, email, password);
        this.fotoUrl = fotoUrl;
    }

    @Override
    public String getNim() {
        return super.getNim();
    }

    @Override
    public String getNama() {
        return super.getNama();
    }

    @Override 
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setNim(String nim) {
        super.setNim(nim);
    }

    @Override
    public void setNama(String nama) {
        super.setNama(nama);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.setNim(mahasiswa.getNim());
        this.setNama(mahasiswa.getNama());
        this.setEmail(mahasiswa.getEmail());
        this.setPassword(mahasiswa.getPassword());
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

    public void setFotoUrl(String fotoUrl){
        this.fotoUrl=fotoUrl;}
    
    public String getFotoUrl(){
        return this.fotoUrl;}

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
