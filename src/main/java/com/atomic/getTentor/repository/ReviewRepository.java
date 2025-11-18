package com.atomic.getTentor.repository;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Tentor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import com.atomic.getTentor.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMenteeId(Integer menteeId);
    List<Review> findByTentorId(Integer tentorId);
    Optional<Review> findByMenteeAndTentor(Mentee mentee, Tentor tentor);
}

