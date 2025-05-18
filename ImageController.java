/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.atomic.getTentor.controller;

/**
 *
 * @author jehez
 */
package com.atomic.getTentor.controller;

import com.atomic.getTentor.model.ImageData;
import com.atomic.getTentor.repository.ImageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final String uploadDir = "uploads/";

    @Autowired
    private ImageDataRepository imageDataRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/image/view/")
                    .path(fileName)
                    .toUriString();

            ImageData imageData = new ImageData();
            imageData.setFileName(fileName);
            imageData.setFileUrl(fileUrl);
            imageDataRepository.save(imageData);

            return ResponseEntity.ok().body("Image URL saved: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed.");
        }
    }

    @GetMapping("/view/{filename:.+}")
    public ResponseEntity<?> viewImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
