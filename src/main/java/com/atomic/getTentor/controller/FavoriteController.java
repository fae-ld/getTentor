package com.atomic.getTentor.controller;

import com.atomic.getTentor.dto.FavoriteDTO;
import com.atomic.getTentor.model.Favorite;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.FavoriteRepository;
import com.atomic.getTentor.repository.MenteeRepository;
import com.atomic.getTentor.repository.TentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private TentorRepository tentorRepository;

    @GetMapping
    public List<FavoriteDTO> getAll() {
        return favoriteRepository.findAll().stream()
                .map(FavoriteDTO::new)
                .toList();
    }

    @GetMapping("/{menteeId}")
    public List<FavoriteDTO> getByMenteeId(@PathVariable Integer menteeId) {
        return favoriteRepository.findByMentee_Id(menteeId).stream()
                .map(FavoriteDTO::new)
                .toList();
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestParam Integer menteeId, @RequestParam Integer tentorId) {
        if (favoriteRepository.existsByMentee_IdAndTentor_Id(menteeId, tentorId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Mentee mentee = menteeRepository.findById(menteeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mentee tidak ditemukan"));
        Tentor tentor = tentorRepository.findById(tentorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tentor tidak ditemukan"));

        Favorite favorite = new Favorite();
        favorite.setMentee(mentee);
        favorite.setTentor(tentor);

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return ResponseEntity.status(HttpStatus.CREATED).body(new FavoriteDTO(savedFavorite));
    }


    @Transactional
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFavorite(@RequestParam Integer menteeId, @RequestParam Integer tentorId) {
        boolean exists = favoriteRepository.existsByMentee_IdAndTentor_Id(menteeId, tentorId);
        if (!exists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorite tidak ditemukan");
        }

        favoriteRepository.deleteByMentee_IdAndTentor_Id(menteeId, tentorId);
    }
}
