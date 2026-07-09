package ru.shift.userimporter.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileProcessingResultDto {
    private Long fileId;
    private String filename;
    private String status;
    private int totalRows;
    private int validRows;
    private int invalidRows;
    private int processedRows;
    private int usersSaved;
    private int errorsCount;
    private List<String> errorMessages;
    private String error;

    public static FileProcessingResultDto error(String message) {
        return FileProcessingResultDto.builder()
                .error(message)
                .build();
    }

    public boolean hasError() {
        return error != null && !error.isEmpty();
    }
}