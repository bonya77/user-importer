package ru.shift.userimporter.core.service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.exception.FileProcessingException;
import ru.shift.userimporter.core.model.FileEntity;
import ru.shift.userimporter.core.model.ProcessingError;
import ru.shift.userimporter.core.model.UserEntity;
import ru.shift.userimporter.core.repository.FileProcessingErrorsRepository;
import ru.shift.userimporter.core.repository.FileRepository;
import ru.shift.userimporter.core.repository.UserRepository;
import ru.shift.userimporter.core.util.FileProcessor;
import ru.shift.userimporter.core.util.ProcessingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final FileProcessingErrorsRepository fileProcessingErrorsRepository;

    @Transactional
    public FileEntity uploadFile(FileEntity fileEntity) {
        if (fileRepository.existsByHash(fileEntity.getHash())) {
            throw new RuntimeException("File with hash" + fileEntity.getHash() +
                    "already exist");
        }
        return fileRepository.save(fileEntity);
    }

    @Transactional
    public boolean deleteFile(FileEntity fileEntity) {
        boolean operationStatus = false;
        if (!fileRepository.existsByHash(fileEntity.getHash())) {
            throw new RuntimeException("File with Hash" + fileEntity.getHash() +
                    "doesnt exist");
        } else {
            fileRepository.delete(fileEntity);
            operationStatus = true;
        }

        return operationStatus;
    }


    @Transactional
    public ProcessingResult startFileProcessing(Long fileId) throws Exception {
        if(!fileRepository.existsById(fileId)){
            throw new FileProcessingException("file with id" + fileId + "doesn't exist");
        }
        FileEntity file = fileRepository.getReferenceById(fileId);
        try{

            ProcessingResult processingResult = FileProcessor.processFile(file);

            List<UserEntity> users = processingResult.getUserEntities();
            List<ProcessingError> processingErrors = processingResult.getProcessingErrors();

            if(!users.isEmpty()){
                userRepository.saveAll(users);
            }

            if(!processingErrors.isEmpty()){
                fileProcessingErrorsRepository.saveAll(processingErrors);
            }

            fileRepository.save(file);
            return processingResult;
        }
        catch(FileProcessingException e){
            throw new FileProcessingException(e.getMessage());
        }
    }

    @Transactional
    public FileEntity findFileById(Long id){
        return fileRepository.findById(id).orElse(null);
    }

}
