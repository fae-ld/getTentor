package com.atomic.getTentor.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.atomic.getTentor.model.MataKuliah;
import com.atomic.getTentor.repository.MataKuliahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.atomic.getTentor.dto.TentorDTO;
import com.atomic.getTentor.model.Mahasiswa;
import com.atomic.getTentor.model.Tentor;
import com.atomic.getTentor.repository.MahasiswaRepository;
import com.atomic.getTentor.repository.TentorRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TentorService {
    @Autowired
    private TentorRepository tentorRepository;

    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Autowired
    private MataKuliahRepository mataKuliahRepository;

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
        mahasiswa.setNoTelp(tentorDTO.getNoTelp());
        if (tentorDTO.getFotoUrl() == null || tentorDTO.getFotoUrl().isEmpty()) {
            mahasiswa.setFotoUrl("http://localhost:8080/api/images/view/default-profile.png");
        } else {
            mahasiswa.setFotoUrl(tentorDTO.getFotoUrl());
        }
        mahasiswa.setPassword(passwordEncoder.encode(tentorDTO.getPassword())); // Hash password

        // 3. Simpen Mahasiswa ke database
        mahasiswaRepository.save(mahasiswa);

        // 4. Buat objek Tentor
        Tentor tentor = new Tentor();
        tentor.setMahasiswa(mahasiswa);
        tentor.setIpk(tentorDTO.getIpk());

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
                    .map(TentorDTO::new)
                    .toList();
        }

        return tentorRepository.searchTentorByKeyword(q).stream()
                .map(TentorDTO::new)
                .toList();
    }


    public void updateTentorProfile(String email, TentorDTO updatedDTO, MultipartFile file) throws Exception {
        Tentor tentor = tentorRepository.findByMahasiswaEmail(email);
        if (tentor == null) throw new RuntimeException("Tentor tidak ditemukan");

        Mahasiswa mhs = tentor.getMahasiswa();

        // Update data umum
        if (updatedDTO != null) {
            if (updatedDTO.getNama() != null) mhs.setNama(updatedDTO.getNama());
            if (updatedDTO.getNoTelp() != null) mhs.setNoTelp(updatedDTO.getNoTelp());
            if (updatedDTO.getIpk() != null) tentor.setIpk(updatedDTO.getIpk());
            if (updatedDTO.getListMataKuliah() != null) {
                List<MataKuliah> validatedList = updatedDTO.getListMataKuliah().stream()
                        .map(mk -> mataKuliahRepository.findById(mk.getId())
                                .orElseThrow(() -> new RuntimeException("Mata kuliah dengan id " + mk.getId() + " tidak ditemukan")))
                        .collect(Collectors.toList());
                tentor.setListMataKuliah(validatedList);
            }

            if (updatedDTO.getPengalaman() != null) {
                tentor.setPengalaman(String.join("|", updatedDTO.getPengalaman()));
            }

            mahasiswaRepository.save(mhs); // Simpan perubahan mahasiswa
        }

        // Handle update foto profil
        if (file != null && !file.isEmpty()) {
            // Hapus file lama jika ada
            if (tentor.getFotoUrl() != null) {
                Path oldFilePath = Paths.get(uploadDir).resolve(tentor.getFotoUrl());
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

        // Simpan update tentor (jika ada)
        tentorRepository.save(tentor);
    }
}
