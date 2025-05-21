package com.atomic.getTentor.service;

import com.atomic.getTentor.dto.ReviewDTO;
import com.atomic.getTentor.dto.ReviewInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Review;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.MenteeRepository;
import com.atomic.getTentor.repository.ReviewRepository;
import com.atomic.getTentor.repository.TentorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private TentorRepository tentorRepository;

    // Simpan review dari DTO
    public ReviewDTO simpanReview(ReviewInputDTO input) {
        Mentee mentee = menteeRepository.findById(input.getMenteeId())
                .orElseThrow(() -> new IllegalArgumentException("Mentee tidak ditemukan"));

        Tentor tentor = tentorRepository.findById(input.getTentorId())
                .orElseThrow(() -> new RuntimeException("Tentor tidak ditemukan"));

        Optional<Review> existing = reviewRepository.findByMenteeAndTentor(mentee, tentor);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Review already exists for this tentor by this mentee");
        }

        Review review = new Review(mentee, tentor, input.getKomentar(), input.getRating());
        Review saved = reviewRepository.save(review);

        return new ReviewDTO(saved);

    }

    public List<Review> getReviewByMentee(Integer menteeId) {
        return reviewRepository.findByMenteeId(menteeId);
    }

    public List<Review> getReviewByTentor(Integer tentorId) {
        return reviewRepository.findByTentorId(tentorId);
    }
}
