package ru.shift.userimporter.api.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.userimporter.api.dto.FileUploadResponseDto;
import ru.shift.userimporter.api.mapper.FileMapper;
import ru.shift.userimporter.core.model.FileEntity;
import ru.shift.userimporter.core.service.FileService;
import ru.shift.userimporter.core.util.CreateFileEntityFromMultipart;
import ru.shift.userimporter.core.dto.FileProcessingResultDto;
import ru.shift.userimporter.core.util.ProcessingResult;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private FileService fileService;
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public FileUploadResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
        FileEntity fileEntity = CreateFileEntityFromMultipart.createFileEntity(file);
        FileEntity saved = fileService.uploadFile(fileEntity);
        return new FileUploadResponseDto(saved.getId(),
                saved.getOriginalFilename(),
                saved.getProcessingStatus().name(),
                "File uploaded successfully");
    }

    @PostMapping("/{id}/processing")
    public ResponseEntity<FileProcessingResultDto> processFile(Long id) {
        try{
            ProcessingResult result = fileService.startFileProcessing(id);
            FileEntity file = fileService.findFileById(id);
            FileProcessingResultDto resultDto = fileMapper.toProcessingResultDto(file, result);

            return ResponseEntity.ok(resultDto);
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FileProcessingResultDto.error("Internal server error"));
        }
    }

}
