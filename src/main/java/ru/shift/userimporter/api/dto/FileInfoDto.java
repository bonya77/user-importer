package ru.shift.userimporter.api.dto;

import lombok.*;
import ru.shift.userimporter.core.model.Status;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto {
    private Status status;
    private Long fileId;
    private String filename;
    private int totalRows;
    private int validRows;
    private int invalidRows;
    private int processedRows;
}
