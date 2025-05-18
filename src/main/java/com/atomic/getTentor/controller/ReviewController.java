package com.atomic.getTentor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.atomic.getTentor.dto.ReviewDTO;
import com.atomic.getTentor.model.Review;
import com.atomic.getTentor.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // POST: Simpan review baru
    @PostMapping
    public ResponseEntity<Review> simpanReview(@RequestBody ReviewDTO reviewDTO) {
        Review saved = reviewService.simpanReview(reviewDTO);
        return ResponseEntity.ok(saved);
    }

    // GET: Review berdasarkan mentee
    @GetMapping("/mentee/{menteeId}")
    public ResponseEntity<List<Review>> getByMentee(@PathVariable Long menteeId) {
        List<Review> reviews = reviewService.getReviewByMentee(menteeId);
        return ResponseEntity.ok(reviews);
    }

    // GET: Review berdasarkan tentor
    @GetMapping("/tentor/{tentorId}")
    public ResponseEntity<List<Review>> getByTentor(@PathVariable Long tentorId) {
        List<Review> reviews = reviewService.getReviewByTentor(tentorId);
        return ResponseEntity.ok(reviews);
    }
}
