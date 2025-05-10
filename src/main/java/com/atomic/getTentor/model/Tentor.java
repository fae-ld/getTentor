package com.atomic.getTentor.model;

import java.util.ArrayList;
import java.util.List;

public class Tentor {
    private double ipk;
    private String pengalaman;
    private MataKuliah listMataKuliah;
    private Review reviews;

    public Tentor(String nama,
                  String email,
                  String username,
                  String password,
                  String alamat,
                  double ipk) 
    {
        this.ipk = ipk;
        this.pengalaman = new pengalaman;
        this.listMataKuliah = new listmatakuliah;
        this.reviews = new reviews;
    }

    public double getIpk() {
        return ipk;
    }

    public void setIpk(double ipk) {
        this.ipk = ipk;
    }

    public void addPengalaman(String newpengalaman) {
        pengalaman.add(newpengalaman);
    }

    public void removePengalaman(String deletepengalaman) {
        pengalaman.remove(deletepengalaman);
    }

    public void login() {
        System.out.println("You have succesfully log-in.");
    }

    public void register() {
        System.out.println("Registrasion affirmative.");
    }

    public void logout() {
        System.out.println("You have succesfully loged-out.");
    }
}
