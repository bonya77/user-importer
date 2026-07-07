package ru.shift.userimporter.core.service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.FileEntity;
import ru.shift.userimporter.core.repository.FileRepository;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserService userService;

    @Transactional
    public FileEntity uploadFile(FileEntity fileEntity) {
        if (fileRepository.existsByHash(fileEntity)) {
            throw new RuntimeException("File with hash" + fileEntity.getHash() +
                    "already exist");
        }
        return fileRepository.save(fileEntity);
    }

    @Transactional
    public boolean deleteFile(FileEntity fileEntity) {
        boolean operationStatus = false;
        if (!fileRepository.existsByHash(fileEntity)) {
            throw new RuntimeException("File with Hash" + fileEntity.getHash() +
                    "doesnt exist");
        } else {
            fileRepository.delete(fileEntity);
            operationStatus = true;
        }

        return operationStatus;
    }


    @Transactional
    public boolean startFileProcessing(){

    }
}
