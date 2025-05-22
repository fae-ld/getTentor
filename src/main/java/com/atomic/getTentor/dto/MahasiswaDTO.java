package com.atomic.getTentor.dto;

import com.atomic.getTentor.model.Mahasiswa;
import jakarta.validation.constraints.NotNull;

public class MahasiswaDTO {
    @NotNull(message = "nim tidak boleh kosong.")
    private String nim;

    @NotNull(message = "nama tidak boleh kosong.")
    private String nama;

    @NotNull(message = "email tidak boleh kosong.")
    private String email;

    public MahasiswaDTO() {}

    public MahasiswaDTO(String nim, String nama, String email) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
    }

    public MahasiswaDTO(Mahasiswa mahasiswa) {
        this.nim = mahasiswa.getNim();
        this.nama = mahasiswa.getNama();
        this.email = mahasiswa.getEmail();
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
