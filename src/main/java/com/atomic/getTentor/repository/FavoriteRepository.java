package com.atomic.getTentor.repository;

import com.atomic.getTentor.model.Favorite;
import com.atomic.getTentor.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    List<Favorite> findByMentee_Id(Integer menteeId);
    boolean existsByMentee_IdAndTentor_Id(Integer menteeId, Integer tentorId);
    void deleteByMentee_IdAndTentor_Id(Integer menteeId, Integer tentorId);
}