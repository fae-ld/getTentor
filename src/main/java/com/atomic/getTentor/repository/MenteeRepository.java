package com.atomic.getTentor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atomic.getTentor.model.Mentee;


@Repository
public interface MenteeRepository extends JpaRepository<Mentee, String> {
    List<Mentee> findByMahasiswaIsNotNull();
    Mentee findByMahasiswaEmail(String email);
    Mentee findByNim(String nim);
    List<Mentee> findByNamaContainingIgnoreCase(String nama);
    boolean existsByEmail(String email);
    boolean existsByNim(String nim);
}