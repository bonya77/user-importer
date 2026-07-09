package ru.shift.userimporter.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shift.userimporter.core.model.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByOriginalFilename(String fileName);
    boolean existsByHash(String hash);
}
