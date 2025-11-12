package com.atomic.getTentor.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "tentor")
public class Tentor extends AbstractMahasiswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Hubungan one-to-one ke Mahasiswa
    @OneToOne
    @JoinColumn(name = "nim", referencedColumnName = "nim", unique = true)
    private Mahasiswa mahasiswa;

    @Column(columnDefinition = "double")
    private Double ipk;

    @Column(columnDefinition = "TEXT")
    private String pengalaman;

    @OneToMany(mappedBy = "tentor")
    private List<Review> listReview = new ArrayList<>();

    @OneToOne(mappedBy= "tentor")
    private ForgotPasswordTentor forgotPasswordTentor;
    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 16)
    private VerificationStatus verificationStatus;

    @Column(columnDefinition="INTEGER")
    private Integer countFavorite;

    


    public Tentor() {}

    public Tentor(Mahasiswa mahasiswa, Double ipk, String pengalaman) {
        this.mahasiswa = mahasiswa;
        this.ipk = ipk;
        this.pengalaman = pengalaman;
        this.countFavorite = 0;
    }

    @ManyToMany
    @JoinTable(
        name = "tentor_matakuliah",
        joinColumns = @JoinColumn(name = "tentor_id"),
        inverseJoinColumns = @JoinColumn(name = "mk_id")
    )
    private List<MataKuliah> listMataKuliah = new ArrayList<MataKuliah>();

    // Getters & Setters
    @Override
    public Integer getId() { return id; }

    @Override
    public String getEmail() {
        return mahasiswa != null ? mahasiswa.getEmail() : null;
    }

    @Override
    public String getNama() { return mahasiswa != null ? mahasiswa.getNama() : null; }

    @Override
    public String getRole() { return "tentor"; }

    @Override
    public String getFotoUrl() { return mahasiswa != null ? mahasiswa.getFotoUrl() : null; }

    @Override
    public String getNoTelp() { return mahasiswa != null ? mahasiswa.getNoTelp() : null; }
    public VerificationStatus getVerificationStatus() {return verificationStatus;}

    public Integer getCountFavorite(){return this.countFavorite;}
    public Mahasiswa getMahasiswa() { return mahasiswa; }
    public void setMahasiswa(Mahasiswa mahasiswa) { this.mahasiswa = mahasiswa; }
    public Double getIpk() { return ipk; }
    public void setIpk(Double ipk) { this.ipk = ipk; }
    public String getPengalaman() { return pengalaman; }
    public void setPengalaman(String pengalaman) { this.pengalaman = pengalaman; }
    public List<MataKuliah> getListMataKuliah() {return this.listMataKuliah;}
    public void setListMataKuliah(List<MataKuliah> listMataKuliah) { this.listMataKuliah = listMataKuliah;}
    public void setVerificationStatus(VerificationStatus verificationStatus) {this.verificationStatus = verificationStatus;
}

    public String getPassword() {
        return mahasiswa != null ? mahasiswa.getPassword() : null;
    }

    public void setFavorite(Integer countFavorite){
        this.countFavorite = countFavorite;
    }

    public void setPassword(String password) { this.mahasiswa.setPassword(password);}

    public List<String> getPengalamanList() {
        if(this.pengalaman == null || this.pengalaman.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.pengalaman.split("\\|"));
    }

    public List<Review> getListReview() {
        return listReview;
    }

    public void setListReview(List<Review> listReview) {
        this.listReview = listReview;
    }

    public double getAverageRating() {
        if (listReview == null || listReview.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Review review : listReview) {
            total += review.getRating();
        }
        return total / listReview.size();
    }

}
