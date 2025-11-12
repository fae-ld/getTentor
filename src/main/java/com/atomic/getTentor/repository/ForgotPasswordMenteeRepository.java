package com.atomic.getTentor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atomic.getTentor.model.ForgotPasswordMentee;

@Repository
public interface ForgotPasswordMenteeRepository extends JpaRepository<ForgotPasswordMentee, Integer> {

}
