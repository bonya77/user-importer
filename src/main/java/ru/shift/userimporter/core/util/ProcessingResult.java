package ru.shift.userimporter.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.shift.userimporter.core.model.ProcessingError;
import ru.shift.userimporter.core.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingResult {
    private final List<ProcessingError> processingErrors = new ArrayList<>();
    private final List<UserEntity> userEntities = new ArrayList<>();
}
