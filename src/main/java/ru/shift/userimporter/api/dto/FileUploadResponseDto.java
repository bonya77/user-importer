package ru.shift.userimporter.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponseDto {
    private Long fileId;
    private String originalFilename;
    private String status;
    private String message;
}