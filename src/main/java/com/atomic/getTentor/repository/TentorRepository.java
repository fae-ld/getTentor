package com.atomic.getTentor.repository;

import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Tentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TentorRepository extends JpaRepository<Tentor, Integer> {
    List<Tentor> findByMahasiswaIsNotNull();
}