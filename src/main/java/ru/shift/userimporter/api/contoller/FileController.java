package ru.shift.userimporter.api.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.userimporter.api.dto.FileInfoDto;
import ru.shift.userimporter.api.dto.FileUploadResponseDto;
import ru.shift.userimporter.api.mapper.FileInfoMapper;
import ru.shift.userimporter.api.mapper.FileMapper;
import ru.shift.userimporter.core.exception.FileProcessingException;
import ru.shift.userimporter.core.model.FileEntity;
import ru.shift.userimporter.core.repository.FileRepository;
import ru.shift.userimporter.core.service.FileService;
import ru.shift.userimporter.core.util.CreateFileEntityFromMultipart;
import ru.shift.userimporter.core.dto.FileProcessingResultDto;
import ru.shift.userimporter.core.util.ProcessingResult;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileMapper fileMapper;
    private final FileInfoMapper fileInfoMapper;

    @PostMapping("/upload")
    public FileUploadResponseDto uploadFile(@RequestParam("file") MultipartFile file) {
        FileEntity fileEntity = CreateFileEntityFromMultipart.createFileEntity(file);
        FileEntity saved = fileService.uploadFile(fileEntity);
        return fileMapper.toUploadResponseDto(saved, "File uploaded successfully");
    }

    @PostMapping("/{id}/processing")
    public ResponseEntity<FileProcessingResultDto> processFile(@PathVariable Long id) {
        try{
            ProcessingResult result = fileService.startFileProcessing(id);
            FileEntity file = fileService.findFileById(id);
            FileProcessingResultDto resultDto = fileMapper.toProcessingResultDto(file, result);

            return ResponseEntity.ok(resultDto);
        }
        catch (FileProcessingException e){
            return ResponseEntity.status(HttpStatus.PROCESSING).body(FileProcessingResultDto.error(e.getMessage()));
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FileProcessingResultDto.error("Internal server error"));
        }
    }

    @GetMapping("/statistics")
    public List<FileInfoDto> getStatistics(){
        List<FileEntity> files = fileService.findAllFiles();
        return fileInfoMapper.toFileInfoDto(files);
    }

    @DeleteMapping("/deleteAllFiles")
    public void deleteFiles(){
        fileService.deleteAllFiles();
    }
}
