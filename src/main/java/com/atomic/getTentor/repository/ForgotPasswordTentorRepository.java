package com.atomic.getTentor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.atomic.getTentor.model.ForgotPasswordTentor;
import com.atomic.getTentor.model.Tentor;

@Repository
public interface ForgotPasswordTentorRepository extends JpaRepository<ForgotPasswordTentor, Integer> {

    @Query("SELECT fp FROM ForgotPasswordTentor fp WHERE fp.otp = :otp AND fp.tentor = :tentor")
    Optional<ForgotPasswordTentor> findByOtpAndTentor(@Param("otp") Integer otp, @Param("tentor") Tentor tentor);
}
