package com.atomic.getTentor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atomic.getTentor.model.Tentor;

public interface TentorRepository extends JpaRepository<Tentor, Integer> {
    List<Tentor> findByMahasiswaIsNotNull();
    Tentor findByMahasiswaEmail(String email);
    List<Tentor> findDistinctByMahasiswa_NimContainsIgnoreCaseOrMahasiswa_NamaContainsIgnoreCaseOrPengalamanContainsIgnoreCase(
            String nim,
            String nama,
            String pengalaman
    );
}