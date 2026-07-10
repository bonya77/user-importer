package ru.shift.userimporter.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shift.userimporter.core.model.ProcessingError;
import ru.shift.userimporter.core.repository.FileProcessingErrorsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessingErrorService {
    private final FileProcessingErrorsRepository fileProcessingErrorsRepository;

    public List<ProcessingError> getErrorsByFileId(Long fileId) {
        return fileProcessingErrorsRepository.findByFileId(fileId);
    }
}