package com.atomic.getTentor.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteId implements Serializable {
    private Integer mentee;
    private Integer tentor;

    public FavoriteId() {}

    public FavoriteId(Integer mentee, Integer tentor) {
        this.mentee = mentee;
        this.tentor = tentor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(mentee, that.mentee) && Objects.equals(tentor, that.tentor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mentee, tentor);
    }
}
