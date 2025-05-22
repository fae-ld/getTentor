package com.atomic.getTentor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewInputDTO {
    @NotNull(message = "Mentee ID tidak boleh kosong")
    private Integer menteeId;

    @NotNull(message = "Tentor ID tidak boleh kosong")
    private Integer tentorId;

    @NotNull(message = "Rating tidak boleh kosong")
    @Min(value = 1, message = "Rating minimal 1")
    @Max(value = 5, message = "Rating maksimal 5")
    private Integer rating;

    @Size(max = 1000, message = "Komentar maksimal 1000 karakter")
    private String komentar;

    // Constructors
    public ReviewInputDTO() {}

    public ReviewInputDTO(Integer menteeId, Integer tentorId, Integer rating, String komentar) {
        this.menteeId = menteeId;
        this.tentorId = tentorId;
        this.rating = rating;
        this.komentar = komentar;
    }

    // Getters and Setters
    public Integer getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(Integer menteeId) {
        this.menteeId = menteeId;
    }

    public Integer getTentorId() {
        return tentorId;
    }

    public void setTentorId(Integer tentorId) {
        this.tentorId = tentorId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
