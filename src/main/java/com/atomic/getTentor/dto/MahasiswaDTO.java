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

    @NotNull(message = "nomor telepon tidak boleh kosong")
    private String noTelp;

    private String fotoUrl;

    public MahasiswaDTO() {}

    public MahasiswaDTO(String nim, String nama, String email, String fotoUrl, String noTelp) {
        this.nim = nim;
        this.nama = nama;
        this.email = email;
        this.fotoUrl = fotoUrl;
        this.noTelp = noTelp;
    }

    public MahasiswaDTO(Mahasiswa mahasiswa) {
        this.nim = mahasiswa.getNim();
        this.nama = mahasiswa.getNama();
        this.email = mahasiswa.getEmail();
        this.fotoUrl = mahasiswa.getFotoUrl();
        this.noTelp = mahasiswa.getNoTelp();
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public String getNoTelp() { return noTelp; }
    public void setNoTelp(String noTelp) { this.noTelp = noTelp; }
}
