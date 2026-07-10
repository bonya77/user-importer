package ru.shift.userimporter.api.mapper;

import org.springframework.stereotype.Component;
import ru.shift.userimporter.api.dto.FileInfoDto;
import ru.shift.userimporter.core.model.FileEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileInfoMapper {

    public FileInfoDto toDto(FileEntity file){
        return FileInfoDto.builder().status(file.getProcessingStatus()).fileId(file.getId()).filename(file.getOriginalFilename()).
                totalRows(file.getTotalRows()).validRows(file.getValidRows()).
                invalidRows(file.getInvalidRows()).processedRows(file.getProcessedRows()).build();
    }

    public List<FileInfoDto> toFileInfoDto(List<FileEntity> files){
        return files.stream().map(this::toDto).collect(Collectors.toList());
    }
}
