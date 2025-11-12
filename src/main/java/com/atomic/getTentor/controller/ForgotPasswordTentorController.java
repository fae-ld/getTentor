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

import com.atomic.getTentor.dto.ChangePasswordTentor;
import com.atomic.getTentor.dto.MailBody;
import com.atomic.getTentor.model.ForgotPasswordTentor;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.ForgotPasswordTentorRepository;
import com.atomic.getTentor.repository.MahasiswaRepository;
import com.atomic.getTentor.repository.TentorRepository;
import com.atomic.getTentor.service.EmailService;



@RestController
@RequestMapping("/api/forgotPasswordTentor")
public class ForgotPasswordTentorController {
    private final TentorRepository tentorRepository;
    private final MahasiswaRepository mahasiswaRepository;
    private final EmailService emailService;
    private final ForgotPasswordTentorRepository forgotPasswordTentorRepository;
    private final BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    public ForgotPasswordTentorController(TentorRepository tentorRepository, MahasiswaRepository mahasiswaRepository, ForgotPasswordTentorRepository forgotPasswordTentorRepository, EmailService emailService) {
        this.tentorRepository = tentorRepository;
        this.mahasiswaRepository = mahasiswaRepository;
        this.forgotPasswordTentorRepository = forgotPasswordTentorRepository;
        this.emailService = emailService;
    }

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
        Tentor tentor = tentorRepository.findByMahasiswaEmail(email);
        if (tentor == null) {
            throw new UsernameNotFoundException("Tolong berikan email yang valid!!");
        }
        
        int otp = otpGenerator();
        MailBody mailBody = new MailBody.Builder()
            .to(email)
            .subject("OTP for Forgot Password Request")
            .text("This is OTP for your Forgot Password request : " + otp)
            .build();

        ForgotPasswordTentor forgot = new ForgotPasswordTentor();
        forgot.setOtp(otp);
        forgot.setExpirationTime(new Date(System.currentTimeMillis() + 2 * 60 * 1000)); // 2 menit
        forgot.setTentor(tentor); 

        forgotPasswordTentorRepository.save(forgot);
        emailService.sendSimpleMessage(mailBody);


        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("verifyOTP/{otp}/{email}")
    public ResponseEntity<String> verifyOTP(@PathVariable Integer otp, @PathVariable String email){
        Tentor tentor = tentorRepository.findByMahasiswaEmail(email);
        if (tentor == null) {
            throw new UsernameNotFoundException("Tolong berikan email yang valid");
        }

        ForgotPasswordTentor fp = forgotPasswordTentorRepository.findByOtpAndTentor(otp, tentor)
            .orElseThrow(() -> new RuntimeException("Nomor OTP invalid untuk email: " + email));
        
        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordTentorRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP telah kadaluarsa", HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP berhasil diverifikasi!");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandlerTentor(@RequestBody ChangePasswordTentor changePasswordTentor, @PathVariable String email) {
        //TODO: process POST request
        
        if (!Objects.equals(changePasswordTentor.password(), changePasswordTentor.repeatPassword())){
            return new ResponseEntity<>("Tolong masukkan Password ulang!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePasswordTentor.password());
        mahasiswaRepository.updatePassword(email, encodedPassword);
        
        return ResponseEntity.ok("Password telah diganti!");
    }
    

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_100, 999_999 );
    }
    
}
