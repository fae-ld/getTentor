package com.atomic.getTentor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "review")

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @ManyToOne
    @JoinColumn(name = "tentor_id")
    private Tentor tentor;

    public Review() {}

    public Review(Mentee mentee, Tentor tentor) {
        this.mentee = mentee;
        this.tentor = tentor;
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
