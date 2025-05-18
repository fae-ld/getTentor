package com.atomic.getTentor.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.atomic.getTentor.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByMenteeId(Long menteeId);
    List<Review> findByTentorId(Long tentorId);
}

