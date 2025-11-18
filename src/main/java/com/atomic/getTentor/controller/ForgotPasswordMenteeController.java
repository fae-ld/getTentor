package com.atomic.getTentor.controller;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atomic.getTentor.dto.ChangePassword;
import com.atomic.getTentor.dto.MailBody;
import com.atomic.getTentor.dto.VerifyOtpRequest;
import com.atomic.getTentor.model.ForgotPasswordMentee;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.repository.ForgotPasswordMenteeRepository;
import com.atomic.getTentor.repository.MahasiswaRepository;
import com.atomic.getTentor.repository.MenteeRepository;
import com.atomic.getTentor.security.JwtService;
import com.atomic.getTentor.service.EmailService;


@RestController
@RequestMapping("/api/forgotPasswordmentee")
public class ForgotPasswordMenteeController {
    private final MenteeRepository menteeRepository;
    private final MahasiswaRepository mahasiswaRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final ForgotPasswordMenteeRepository forgotPasswordMenteeRepository;
    private final BCryptPasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();

    public ForgotPasswordMenteeController(MenteeRepository menteeRepository, MahasiswaRepository mahasiswaRepository, EmailService emailService, JwtService jwtService, ForgotPasswordMenteeRepository forgotPasswordMenteeRepository){
        this.menteeRepository = menteeRepository;
        this.mahasiswaRepository = mahasiswaRepository;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.forgotPasswordMenteeRepository = forgotPasswordMenteeRepository;
    }

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
        Mentee mentee = menteeRepository.findByMahasiswaEmail(email);
        if (mentee == null) {
            return ResponseEntity.badRequest().body("Email tidak ditemukan");
        }
        
        int otp = otpGenerator();
        MailBody mailBody = new MailBody.Builder()
            .to(email)
            .subject("OTP for Forgot Password Request")
            .text("This is OTP for your Forgot Password request : " + otp)
            .build();

        ForgotPasswordMentee forgot = new ForgotPasswordMentee();
        forgot.setOtp(otp);
        forgot.setExpirationTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 menit
        forgot.setMentee(mentee); 

        forgotPasswordMenteeRepository.save(forgot);
        emailService.sendSimpleMessage(mailBody);


        return ResponseEntity.ok("Email sent for verification!");
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<Map<String, String>> verifyOTP(@RequestBody VerifyOtpRequest request){
        Mentee mentee = menteeRepository.findByMahasiswaEmail(request.email());

        ForgotPasswordMentee fp;
        
        if (mentee == null) {
            throw new UsernameNotFoundException("Tolong berikan email yang valid");
        }

        try{
            fp = forgotPasswordMenteeRepository.findByOtpAndMentee(request.otp(), mentee)
            .orElseThrow(() -> new RuntimeException("Nomor OTP invalid untuk email: " + request.email()));
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message","OTP Tidak Sesuai"));
        }
        
        
        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordMenteeRepository.deleteById(fp.getFpid());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(Map.of("message", "OTP telah kadaluarsa"));
        }

        String resetToken = jwtService.generateResetPasswordToken(request.email());

         // Hapus record OTP
        forgotPasswordMenteeRepository.deleteById(fp.getFpid());

        return ResponseEntity.ok(Map.of(
                "message", "OTP berhasil diverifikasi",
                "resetToken", resetToken
        ));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePasswordHandlerMentee(@RequestBody ChangePassword changePassword) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())){
            return new ResponseEntity<>("Tolong masukkan Password ulang!", HttpStatus.EXPECTATION_FAILED);
        }

        String token = changePassword.resetToken();

        // Validasi token tidak null
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Reset token tidak boleh kosong!");
        }

        // Extract email dari token
        String email;
        try {
            email = jwtService.getEmailFromToken(token);
            
            // Validasi token
            if (!jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Reset token tidak valid atau sudah expired!");
            }
            
            // Cek apakah token reset password
            if (!jwtService.isResetPasswordToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Token ini bukan token reset password!");
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Reset token tidak valid atau sudah expired!");
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        mahasiswaRepository.updatePassword(email, encodedPassword);
        
        return ResponseEntity.ok("Password telah diganti!");
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_100, 999_999 );
    }
}
