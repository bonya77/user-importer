package ru.shift.userimporter.core.util;

import lombok.Data;
import ru.shift.userimporter.core.model.ProcessingError;
import ru.shift.userimporter.core.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProcessingResult {
    private final List<ProcessingError> processingErrors = new ArrayList<>();
    private final List<UserEntity> userEntities = new ArrayList<>();
    private int totalRows;
    private int validRows;
    private int invalidRows;
    private boolean success;
}