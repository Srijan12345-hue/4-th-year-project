package com.group4.th.year.project.smart.campus.Management.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private final Path uploadDirectory;

    public FileStorageService(@Value("${app.upload-dir:uploads}") String uploadDirectory) throws IOException {
        this.uploadDirectory = Path.of(uploadDirectory).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadDirectory);
    }

    public String storePdf(MultipartFile file) throws IOException {
        if (file.isEmpty() || !"application/pdf".equalsIgnoreCase(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files can be uploaded.");
        }

        String fileName = UUID.randomUUID() + ".pdf";
        Files.copy(file.getInputStream(), uploadDirectory.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public String storeImage(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        if (file.isEmpty() || contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files can be uploaded.");
        }

        String extension = contentType.equals("image/png") ? ".png"
                : contentType.equals("image/webp") ? ".webp" : ".jpg";
        String fileName = UUID.randomUUID() + extension;
        Files.copy(file.getInputStream(), uploadDirectory.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public Path getFile(String fileName) {
        Path file = uploadDirectory.resolve(fileName).normalize();
        if (!file.startsWith(uploadDirectory) || !Files.isRegularFile(file)) {
            throw new IllegalArgumentException("Attachment not found.");
        }
        return file;
    }
}
