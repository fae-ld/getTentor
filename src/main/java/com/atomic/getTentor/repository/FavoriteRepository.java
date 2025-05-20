package com.example.favorite.repository;

import com.example.favorite.model.Favorite;
import com.example.favorite.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    List<Favorite> findByMenteeId(Integer menteeId);
}
