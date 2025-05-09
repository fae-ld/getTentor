package com.atomic.getTentor.repository;

import com.atomic.getTentor.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, String> {
    List<Mahasiswa> findByNamaContaining(String nama);
}