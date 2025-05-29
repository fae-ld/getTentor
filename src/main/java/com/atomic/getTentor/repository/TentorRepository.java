package com.atomic.getTentor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.atomic.getTentor.model.Tentor;

public interface TentorRepository extends JpaRepository<Tentor, Integer> {
    @EntityGraph(attributePaths = {"listMataKuliah"})
    Optional<Tentor> findWithMataKuliahById(Integer id);

    List<Tentor> findByMahasiswaIsNotNull();
    Tentor findByMahasiswaEmail(String email);
    List<Tentor> findDistinctByMahasiswa_NimContainsIgnoreCaseOrMahasiswa_NamaContainsIgnoreCaseOrPengalamanContainsIgnoreCase(
            String nim,
            String nama,
            String pengalaman
    );
}