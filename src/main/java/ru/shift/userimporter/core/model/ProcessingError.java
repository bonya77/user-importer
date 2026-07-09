package ru.shift.userimporter.core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file_processing_errors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private Long fileId;

    @Column(name = "row_number", nullable = false)
    private int rowNumber;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "raw_data")
    private String rawData;

}
