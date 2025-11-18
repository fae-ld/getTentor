package com.atomic.getTentor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.atomic.getTentor.model.Mahasiswa;

@Repository
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, String> {
    List<Mahasiswa> findByNamaContaining(String nama);
    List<Mahasiswa> findByNamaContainingIgnoreCase(String nama);
    boolean existsByEmail(String email);
    boolean existsByNim(String nim);

    @Modifying
    @Transactional
    @Query("UPDATE Mahasiswa m SET m.password = :password WHERE m.email = :email")
    void updatePassword(@Param("email") String email, @Param("password") String password);

}
