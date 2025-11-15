package com.atomic.getTentor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atomic.getTentor.model.Mentee;


@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Integer> {
    List<Mentee> findByMahasiswaIsNotNull();
    Mentee findByMahasiswaEmail(String email);
    Mentee findByMahasiswaNim(String nim);
    List<Mentee> findByMahasiswaNamaContainingIgnoreCase(String nama);
    boolean existsByMahasiswaEmail(String email);
    boolean existsByMahasiswaNim(String nim);
}