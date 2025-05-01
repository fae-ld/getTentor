package com.atomic.getTentor.repository;

import com.atomic.getTentor.model.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Long> {
}
