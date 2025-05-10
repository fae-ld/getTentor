package com.atomic.getTentor.repository;

import com.atomic.getTentor.model.Tentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TentorRepository extends JpaRepository<Tentor, Integer> {}