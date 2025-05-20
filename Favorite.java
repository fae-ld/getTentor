package com.example.favorite.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(FavoriteId.class)
public class Favorite {
    @Id
    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;

    @Id
    @ManyToOne
    @JoinColumn(name = "tentor_id")
    private Tentor tentor;

    // Getters and setters
}
