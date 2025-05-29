package com.atomic.getTentor.dto;

import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Review;
import com.atomic.getTentor.model.Tentor;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Integer id;
    private Integer rating;
    private MenteeDTO mentee;
    private String komentar;
    private String reviewerNama;
    private String reviewerNim;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.komentar = review.getKomentar();

        if (review.getMentee() != null && review.getMentee().getMahasiswa() != null) {
            this.mentee = new MenteeDTO(review.getMentee());
            this.reviewerNama = review.getMentee().getMahasiswa().getNama();
            this.reviewerNim = review.getMentee().getMahasiswa().getNim();
        } else {
            this.reviewerNama = "Anonim";
            this.reviewerNim = "-";
        }

        this.createdAt = review.getCreatedAt();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getKomentar() { return komentar; }
    public void setKomentar(String komentar) { this.komentar = komentar; }

    public String getReviewerNama() { return reviewerNama; }
    public void setReviewerNama(String reviewerNama) { this.reviewerNama = reviewerNama; }

    public String getReviewerNim() { return reviewerNim; }
    public void setReviewerNim(String reviewerNim) { this.reviewerNim = reviewerNim; }

    public MenteeDTO getMentee() {return mentee;}
    public void setMentee(MenteeDTO mentee) {this.mentee = mentee;}

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
