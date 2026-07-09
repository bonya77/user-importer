package ru.shift.userimporter.core.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.userimporter.core.model.FileEntity;
import ru.shift.userimporter.core.model.Status;

import java.io.IOException;

@UtilityClass
public class CreateFileEntityFromMultipart {
    public FileEntity createFileEntity(MultipartFile file) {
        try {
            String storagePath = FileStorageUtil.saveFileToDisk(file);

            String hash = HashUtil.generateHash(file.getBytes());

            FileEntity fileEntity = new FileEntity();
            fileEntity.setOriginalFilename(file.getOriginalFilename());
            fileEntity.setStoragePath(storagePath);
            fileEntity.setHash(hash);
            fileEntity.setProcessingStatus(Status.NEW);

            return fileEntity;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }
}
