package ru.shift.userimporter.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.shift.userimporter.core.model.ProcessingError;
import ru.shift.userimporter.core.model.Status;
import ru.shift.userimporter.core.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProcessingResult {
    private final List<ProcessingError> processingErrors = new ArrayList<>();
    private final List<UserEntity> userEntities = new ArrayList<>();
    private int totalRows;
    private int ValidRows;
    private int invalidRows;
    private boolean success;
}