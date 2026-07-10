package ru.shift.userimporter.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "uploaded_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "storage_path", nullable = false, unique = true)
    private String storagePath;

    @Column(name = "total_rows")
    private int totalRows;

    @Column(name = "valid_rows")
    private int validRows;

    @Column(name = "invalid_rows")
    private int invalidRows;

    @Column(name = "processed_rows")
    private int processedRows;

    @Column(name = "hash", nullable = false, unique = true)
    private String hash;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status processingStatus;


}
