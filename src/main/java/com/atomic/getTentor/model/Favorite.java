package com.atomic.getTentor.model;

import jakarta.persistence.*;

@Entity
@IdClass(FavoriteId.class)
@Table(name = "favorite")
public class Favorite {
    @Id
    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @Id
    @ManyToOne
    @JoinColumn(name = "tentor_id")
    private Tentor tentor;

    public Favorite() {}

    // Getters and setters
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