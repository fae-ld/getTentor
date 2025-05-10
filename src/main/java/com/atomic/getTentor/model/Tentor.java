package com.atomic.getTentor.model;

import java.util.ArrayList;
import java.util.List;

public class Tentor {
    private double ipk;
    private List<String> pengalaman;
    private List<MataKuliah> listMataKuliah;
    private List<Review> reviews;

    // Constructor
    public Tentor(String nama, String email, String username, String password, String alamat, double ipk) {
        this.ipk = ipk;
        this.pengalaman = new ArrayList<>();
        this.listMataKuliah = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    // Getter dan Setter untuk ipk
    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }

    // Menambahkan pengalaman
    public void addPengalaman(String pengalamanBaru) {
        pengalaman.add(pengalamanBaru);
    }

    // Menghapus pengalaman
    public void removePengalaman(String pengalamanUntukDihapus) {
        pengalaman.remove(pengalamanUntukDihapus);
    }

    // Method login
    public void login() {
        System.out.println("Login berhasil.");
    }

    // Method register
    public void register() {
        System.out.println("Registrasi berhasil.");
    }

    // Method logout
    public void logout() {
        System.out.println("Logout berhasil.");
    }
}
