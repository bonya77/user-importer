package ru.shift.userimporter.core.exception;

public class EmptyFileException extends FileProcessingException {
    public EmptyFileException(String message) {
        super(message);
    }
}
