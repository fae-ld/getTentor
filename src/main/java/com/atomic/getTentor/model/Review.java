package com.atomic.getTentor.model;

import jakarta.persistence.*;


@Entity
@Table(
        name = "review",
        uniqueConstraints = @UniqueConstraint(columnNames = {"mentee_id", "tentor_id"})
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mentee_id", nullable = false)
    private Mentee mentee;

    @ManyToOne
    @JoinColumn(name = "tentor_id", nullable = false)
    private Tentor tentor;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String komentar;

    @Column(nullable = false)
    private Integer rating;



    public Review() {}

    public Review(Mentee mentee, Tentor tentor,String komentar, Integer rating) {
        this.mentee = mentee;
        this.tentor = tentor;
        this.komentar = komentar;
        this.rating = rating;
    }

    public String getKomentar(){
        return this.komentar;
    }

    public void setKomentar(String komentar){
        this.komentar = komentar;
    }

    public Integer getRating(){
        return this.rating;
    }

    public void setRating(Integer rating){
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Mentee getMentee() {
        return mentee;
    }
    public void setMentee(Mentee mentee) {
        this.mentee = mentee;
    }

    public Tentor getTentor() {
        return tentor;
    }
    public void setTentor(Tentor tentor) {
        this.tentor = tentor;
    }
}
