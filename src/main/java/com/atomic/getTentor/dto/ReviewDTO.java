package com.atomic.getTentor.dto;

import com.atomic.getTentor.model.Review;

public class ReviewDTO {
    private Integer id;
    private double rating;
    private String komentar;
    private String reviewerNama;
    private String reviewerNim;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.komentar = review.getKomentar();


        if (review.getMentee() != null && review.getMentee() != null) {
            this.reviewerNama = review.getMentee().getMahasiswa().getNama();
            this.reviewerNim = review.getMentee().getMahasiswa().getNim();
        } else {
            this.reviewerNama = "Anonim";
            this.reviewerNim = "-";
        }
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getKomentar() { return komentar; }
    public void setKomentar(String komentar) { this.komentar = komentar; }

    public String getReviewerNama() { return reviewerNama; }
    public void setReviewerNama(String reviewerNama) { this.reviewerNama = reviewerNama; }

    public String getReviewerNim() { return reviewerNim; }
    public void setReviewerNim(String reviewerNim) { this.reviewerNim = reviewerNim; }
}
