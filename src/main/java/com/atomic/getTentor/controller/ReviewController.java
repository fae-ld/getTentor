package com.atomic.getTentor.controller;

import com.atomic.getTentor.dto.ReviewInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.atomic.getTentor.dto.ReviewDTO;
import com.atomic.getTentor.model.Review;
import com.atomic.getTentor.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // POST: Simpan review baru
    @PostMapping
    public ResponseEntity<ReviewDTO> simpanReview(@RequestBody ReviewInputDTO input) {
        ReviewDTO saved = reviewService.simpanReview(input);
        return ResponseEntity.ok(saved);
    }

    // GET: Review berdasarkan mentee
    @GetMapping("/mentee/{menteeId}")
    public ResponseEntity<List<ReviewDTO>> getByMentee(@PathVariable Integer menteeId) {
        List<Review> reviews = reviewService.getReviewByMentee(menteeId);
        List<ReviewDTO> dtos = reviews.stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET: Review berdasarkan tentor
    @GetMapping("/tentor/{tentorId}")
    public ResponseEntity<List<ReviewDTO>> getByTentor(@PathVariable Integer tentorId) {
        List<Review> reviews = reviewService.getReviewByTentor(tentorId);
        List<ReviewDTO> dtos = reviews.stream()
                .map(ReviewDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
