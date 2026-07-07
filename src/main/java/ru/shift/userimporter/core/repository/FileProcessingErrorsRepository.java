package ru.shift.userimporter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shift.userimporter.core.model.ProcessingError;

import java.util.List;

@Repository
public interface FileProcessingErrorsRepository extends JpaRepository<ProcessingError, Long> {
    List<ProcessingError> findByFileId(Long fileId);
    void deleteByFileId(Long fileId);
}
