package com.atomic.getTentor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.atomic.getTentor.dto.ReviewDTO;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Review;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.MenteeRepository;
import com.atomic.getTentor.repository.ReviewRepository;
import com.atomic.getTentor.repository.TentorRepository;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private TentorRepository tentorRepository;

    // Simpan review dari DTO
    public Review simpanReview(ReviewDTO dto) {
        Mentee mentee = menteeRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Mentee tidak ditemukan"));
        Tentor tentor = tentorRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Tentor tidak ditemukan"));

        Review review = new Review();
        review.setKomentar(dto.getKomentar());
        review.setRating(dto.getRating());
        review.setMentee(mentee);
        review.setTentor(tentor);

        return reviewRepository.save(review);
    }

    public List<Review> getReviewByMentee(Long menteeId) {
        return reviewRepository.findByMenteeId(menteeId);
    }

    public List<Review> getReviewByTentor(Long tentorId) {
        return reviewRepository.findByTentorId(tentorId);
    }
}

