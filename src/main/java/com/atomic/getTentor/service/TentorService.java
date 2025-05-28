package com.atomic.getTentor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.MahasiswaRepository;
import com.atomic.getTentor.repository.TentorRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class TentorService {
    @Autowired
    private TentorRepository tentorRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public List<TentorDTO> getAllTentors() {
        List<Tentor> tentors = tentorRepository.findByMahasiswaIsNotNull();
        return tentors.stream()
                .map(TentorDTO::new)
                .collect(Collectors.toList());
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void login(String email, String password) {
        Tentor tentor = tentorRepository.findByMahasiswaEmail(email);
        if (tentor == null || !passwordEncoder.matches(password, tentor.getMahasiswa().getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
    }
    public void register(TentorDTO tentorDTO) {
        // 1. Cek email ada ga
        if (mahasiswaRepository.existsByEmail(tentorDTO.getEmail())) {
            throw new RuntimeException("Email sudah digunakan!");
        }

        // 2. Buat objek Mahasiswa
        Mahasiswa mahasiswa = new Mahasiswa();
        mahasiswa.setNim(tentorDTO.getNim());
        mahasiswa.setNama(tentorDTO.getNama());
        mahasiswa.setEmail(tentorDTO.getEmail());
        mahasiswa.setPassword(passwordEncoder.encode(tentorDTO.getPassword())); // Hash password

        // 3. Simpen Mahasiswa ke database
        mahasiswaRepository.save(mahasiswa);

        // 4. Buat objek Tentor
        Tentor tentor = new Tentor();
        tentor.setMahasiswa(mahasiswa);
        tentor.setIpk(tentorDTO.getIpk());
        tentor.setNoTelp(tentorDTO.getNoTelp());
        if (tentor.getFotoUrl() == null || tentor.getFotoUrl().isEmpty()) {
            tentor.setFotoUrl("http://localhost:8080/api/images/view/default-profile.png");
        }else {
            tentor.setFotoUrl(tentorDTO.getFotoUrl());
        }


        // Gabungin List<String> pengalaman jadi String with pemisah "|"
        String pengalaman = tentorDTO.getPengalaman() != null
                ? String.join("|", tentorDTO.getPengalaman())
                : "";
        tentor.setPengalaman(pengalaman);

        // 5. Simpen Tentor ke database
        tentorRepository.save(tentor);
    }
    public List<TentorDTO> searchTentors(String q) {
        if (q == null || q.isBlank()) {
            return tentorRepository.findAll().stream()
                    .map(TentorDTO::new).toList();
        }
        return tentorRepository.findDistinctByMahasiswa_NimContainsIgnoreCaseOrMahasiswa_NamaContainsIgnoreCaseOrPengalamanContainsIgnoreCase(q,q,q).stream().map(TentorDTO::new).toList();
    }

    public void updateTentorProfile(String email, TentorDTO updatedDTO, MultipartFile file) throws Exception {
        Tentor tentor = tentorRepository.findByMahasiswaEmail(email);
        if (tentor == null) throw new RuntimeException("Tentor tidak ditemukan");

        // Update data
        if(updatedDTO != null) {
            Mahasiswa mhs = tentor.getMahasiswa();
            if (updatedDTO.getNama() != null) mhs.setNama(updatedDTO.getNama());
            if (updatedDTO.getNoTelp() != null) tentor.setNoTelp(updatedDTO.getNoTelp());
            if (updatedDTO.getIpk() != null) tentor.setIpk(updatedDTO.getIpk());

            if (updatedDTO.getPengalaman() != null) {
                tentor.setPengalaman(String.join("|", updatedDTO.getPengalaman()));
            }

            mahasiswaRepository.save(mhs);
        }

        // TODO: Update password?

        // If ada file lama, delete
        if (tentor.getFotoUrl() != null) {
            Path oldPath = Paths.get(uploadDir).resolve(tentor.getFotoUrl());
            Files.deleteIfExists(oldPath);
        }

        // Handle foto profil if ada
        if (file != null && !file.isEmpty()) {
            // Simpen file
            Files.createDirectories(Paths.get(uploadDir));
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;
            Path filePath = Paths.get(uploadDir).resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/images/view/")
                    .path(uniqueFileName)
                    .toUriString();
            tentor.setFotoUrl(fileUrl);
        }

        // Simpen perubahan
        tentorRepository.save(tentor);
    }
}
