package com.atomic.getTentor.controller;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atomic.getTentor.dto.ChangePasswordMentee;
import com.atomic.getTentor.dto.MailBody;
import com.atomic.getTentor.model.ForgotPasswordMentee;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.repository.ForgotPasswordMenteeRepository;
import com.atomic.getTentor.repository.MahasiswaRepository;
import com.atomic.getTentor.repository.MenteeRepository;
import com.atomic.getTentor.service.EmailService;


@RestController
@RequestMapping("/api/forgotPasswordMentee")
public class ForgotPasswordMenteeController {
    private final MenteeRepository menteeRepository;
    private final MahasiswaRepository mahasiswaRepository;
    private final EmailService emailService;
    private final ForgotPasswordMenteeRepository forgotPasswordMenteeRepository;
    private final BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    public ForgotPasswordMenteeController(MenteeRepository menteeRepository, MahasiswaRepository mahasiswaRepository, EmailService emailService, ForgotPasswordMenteeRepository forgotPasswordMenteeRepository){
        this.menteeRepository = menteeRepository;
        this.mahasiswaRepository = mahasiswaRepository;
        this.emailService = emailService;
        this.forgotPasswordMenteeRepository = forgotPasswordMenteeRepository;
    }

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
        Mentee mentee = menteeRepository.findByMahasiswaEmail(email);
        if (mentee == null) {
            throw new UsernameNotFoundException("Tolong berikan email yang valid!!");
        }
        
        int otp = otpGenerator();
        MailBody mailBody = new MailBody.Builder()
            .to(email)
            .subject("OTP for Forgot Password Request")
            .text("This is OTP for your Forgot Password request : " + otp)
            .build();

        ForgotPasswordMentee forgot = new ForgotPasswordMentee();
        forgot.setOtp(otp);
        forgot.setExpirationTime(new Date(System.currentTimeMillis() + 2 * 60 * 1000)); // 2 menit
        forgot.setMentee(mentee); 

        forgotPasswordMenteeRepository.save(forgot);
        emailService.sendSimpleMessage(mailBody);


        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("verifyOTP/{otp}/{email}")
    public ResponseEntity<String> verifyOTP(@PathVariable Integer otp, @PathVariable String email){
        Mentee mentee = menteeRepository.findByMahasiswaEmail(email);
        if (mentee == null) {
            throw new UsernameNotFoundException("Tolong berikan email yang valid");
        }

        ForgotPasswordMentee fp = forgotPasswordMenteeRepository.findByOtpAndMentee(otp, mentee)
            .orElseThrow(() -> new RuntimeException("Nomor OTP invalid untuk email: " + email));
        
        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordMenteeRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP telah kadaluarsa", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP berhasil diverifikasi!");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandlerMentee(@RequestBody ChangePasswordMentee changePasswordMentee, @PathVariable String email) {
        //TODO: process POST request
        
        if (!Objects.equals(changePasswordMentee.password(), changePasswordMentee.repeatPassword())){
            return new ResponseEntity<>("Tolong masukkan Password ulang!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePasswordMentee.password());
        mahasiswaRepository.updatePassword(email, encodedPassword);
        
        return ResponseEntity.ok("Password telah diganti!");
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_100, 999_999 );
    }
}
