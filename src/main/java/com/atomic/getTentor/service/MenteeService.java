package com.atomic.getTentor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.model.Tentor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atomic.getTentor.dto.MenteeDTO;
import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Mentee;
import com.atomic.getTentor.repository.MahasiswaRepository;
import com.atomic.getTentor.repository.MenteeRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class MenteeService {
    @Autowired
    private MenteeRepository menteeRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public List<MenteeDTO> getAllMentees() {
        List<Mentee> mentees = menteeRepository.findByMahasiswaIsNotNull();
        return mentees.stream()
                .map(MenteeDTO::new)
                .collect(Collectors.toList());
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public void login(String email, String password) {
        Mentee mentee = menteeRepository.findByMahasiswaEmail(email);
        if (mentee == null || !passwordEncoder.matches(password, mentee.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    public void register(MenteeDTO menteeDTO) {
        // 1. Cek apakah email sudah ada
        if (mahasiswaRepository.existsByEmail(menteeDTO.getEmail())) {
            throw new RuntimeException("Email sudah digunakan!");
        }

        if (mahasiswaRepository.existsByNim(menteeDTO.getNim())){
            throw new RuntimeException("Nim sudah digunakan!");
        }

        // 2. Membuat objek Mahasiswa
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNim(menteeDTO.getNim());
        mahasiswa.setNama(menteeDTO.getNama());
        mahasiswa.setEmail(menteeDTO.getEmail());
        mahasiswa.setNoTelp(menteeDTO.getNoTelp());
        mahasiswa.setPassword(passwordEncoder.encode(menteeDTO.getPassword())); // Hash password

        // 3. Menyimpan Mahasiswa ke database
        mahasiswaRepository.save(mahasiswa);

        // 4. Membuat objek Mentee
        Mentee mentee = new Mentee();
        mentee.setMahasiswa(mahasiswa);

        // 5. Menyimpan Mentee ke database
        menteeRepository.save(mentee);
    }

    public void updateMenteeProfile(String email, MenteeDTO updatedDTO, MultipartFile file) throws Exception {
        Mentee mentee = menteeRepository.findByMahasiswaEmail(email);
        if (mentee == null) throw new RuntimeException("Mentee tidak ditemukan");

        Mahasiswa mhs = mentee.getMahasiswa();

        // Update data umum
        if (updatedDTO != null) {
            if (updatedDTO.getNama() != null) mhs.setNama(updatedDTO.getNama());
            if (updatedDTO.getNoTelp() != null) mhs.setNoTelp(updatedDTO.getNoTelp());
            mahasiswaRepository.save(mhs); // Simpan perubahan mahasiswa
        }

        // Handle update foto profil
        if (file != null && !file.isEmpty()) {
            // Hapus file lama jika ada
            if (mentee.getFotoUrl() != null) {
                Path oldFilePath = Paths.get(uploadDir).resolve(mentee.getFotoUrl());
                Files.deleteIfExists(oldFilePath);
            }

            // Simpan file baru
            Files.createDirectories(Paths.get(uploadDir)); // Buat folder jika belum ada
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
            Path filePath = Paths.get(uploadDir).resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            mhs.setFotoUrl(uniqueFileName); // Simpan hanya nama file
            mahasiswaRepository.save(mhs);
        }

        // Simpan perubahan mentee
        menteeRepository.save(mentee);
    }
}
