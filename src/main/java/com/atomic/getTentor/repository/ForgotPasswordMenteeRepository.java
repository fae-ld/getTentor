package com.atomic.getTentor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.atomic.getTentor.model.ForgotPasswordMentee;
import com.atomic.getTentor.model.Mentee;

@Repository
public interface ForgotPasswordMenteeRepository extends JpaRepository<ForgotPasswordMentee, Integer> {

    @Query("SELECT fp FROM ForgotPasswordMentee fp WHERE fp.otp = :otp AND fp.mentee = :mentee")
    Optional<ForgotPasswordMentee> findByOtpAndMentee(@Param("otp") Integer otp, @Param("mentee") Mentee mentee);
}
