package com.atomic.getTentor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.atomic.getTentor.model.Tentor;

public interface TentorRepository extends JpaRepository<Tentor, Integer> {
    @EntityGraph(attributePaths = {"listMataKuliah"})
    Optional<Tentor> findWithMataKuliahById(Integer id);

    List<Tentor> findByMahasiswaIsNotNull();
    Tentor findByMahasiswaEmail(String email); 

    @Query("SELECT DISTINCT t FROM Tentor t " +
       "LEFT JOIN t.mahasiswa m " +
       "LEFT JOIN t.listMataKuliah mk " +
       "WHERE LOWER(m.nim) LIKE LOWER(CONCAT('%', :q, '%')) " +
       "   OR LOWER(m.nama) LIKE LOWER(CONCAT('%', :q, '%')) " +
       "   OR LOWER(t.pengalaman) LIKE LOWER(CONCAT('%', :q, '%')) " +
       "   OR LOWER(mk.nama) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Tentor> searchTentorByKeyword(@org.springframework.data.repository.query.Param("q") String q);

}