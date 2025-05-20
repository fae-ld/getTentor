package com.example.favorite.controller;

import com.example.favorite.model.Favorite;
import com.example.favorite.model.FavoriteId;
import com.example.favorite.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping
    public List<Favorite> getAll() {
        return favoriteRepository.findAll();
    }

    @GetMapping("/{menteeId}")
    public List<Favorite> getByMentee(@PathVariable Integer menteeId) {
        return favoriteRepository.findByMenteeId(menteeId);
    }

    @PostMapping
    public Favorite addFavorite(@RequestBody Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    @DeleteMapping
    public void deleteFavorite(@RequestBody Favorite favorite) {
        favoriteRepository.delete(favorite);
    }
}
