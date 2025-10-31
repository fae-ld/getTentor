package com.atomic.getTentor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atomic.getTentor.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer>{
    Admin findByMahasiswaEmail(String email); 
}
