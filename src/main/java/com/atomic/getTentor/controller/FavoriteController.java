package com.atomic.getTentor.controller;

import com.atomic.getTentor.dto.TentorDTO;
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

    @GetMapping("/{menteeId}")
    public List<TentorDTO> getFavoritesByMentee(@PathVariable Integer menteeId) {
        List<Favorite> favorites = favoriteRepository.findByMentee_Id(menteeId);
        return favorites.stream()
                .map(fav -> new TentorDTO(fav.getTentor()))
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFavorite(@RequestParam Integer menteeId, @RequestParam Integer tentorId) {
        // Cek apakah kombinasi sudah ada
        boolean exists = favoriteRepository.existsByMentee_IdAndTentor_Id(menteeId, tentorId);
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Favorite sudah ada");
        }

        // Ambil entity asli dari db
        Mentee mentee = menteeRepository.findById(menteeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mentee tidak ditemukan"));
        Tentor tentor = tentorRepository.findById(tentorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tentor tidak ditemukan"));

        // Simpen Favorite baru
        Favorite favorite = new Favorite();
        favorite.setMentee(mentee);
        favorite.setTentor(tentor);
        favoriteRepository.save(favorite);
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