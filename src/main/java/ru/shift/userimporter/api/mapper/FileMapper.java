package ru.shift.userimporter.api.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.shift.userimporter.api.dto.FileUploadResponseDto;
import ru.shift.userimporter.api.mapper.UserMapper;
import ru.shift.userimporter.core.model.FileEntity;
import ru.shift.userimporter.core.model.ProcessingError;
import ru.shift.userimporter.core.util.ProcessingResult;
import ru.shift.userimporter.core.dto.FileProcessingResultDto;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileMapper {

    private final UserMapper userMapper;

    public FileUploadResponseDto toUploadResponseDto(FileEntity entity, String message) {
        return FileUploadResponseDto.builder()
                .fileId(entity.getId())
                .originalFilename(entity.getOriginalFilename())
                .status(entity.getProcessingStatus().name())
                .message(message)
                .build();
    }

    public FileProcessingResultDto toProcessingResultDto(FileEntity file, ProcessingResult result) {
        return FileProcessingResultDto.builder()
                .fileId(file.getId())
                .filename(file.getOriginalFilename())
                .status(file.getProcessingStatus().name())
                .totalRows(result.getTotalRows())
                .validRows(result.getValidRows())
                .invalidRows(result.getInvalidRows())
                .usersSaved(result.getUserEntities().size())
                .errorsCount(result.getProcessingErrors().size())
                .errorMessages(result.getProcessingErrors().stream()
                        .map(ProcessingError::getErrorMessage)
                        .collect(Collectors.toList()))
                .build();
    }
}