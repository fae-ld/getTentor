package com.atomic.getTentor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atomic.getTentor.model.Tentor;

public interface TentorRepository extends JpaRepository<Tentor, Integer> {
    List<Tentor> findByMahasiswaIsNotNull();
    Tentor findByEmail(String email);
}