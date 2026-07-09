package ru.shift.userimporter.core.util;

import lombok.experimental.UtilityClass;
import ru.shift.userimporter.core.exception.EmptyFileException;
import ru.shift.userimporter.core.exception.FileProcessingException;
import ru.shift.userimporter.core.exception.FileReadException;
import ru.shift.userimporter.core.model.FileEntity;
import ru.shift.userimporter.core.model.ProcessingError;
import ru.shift.userimporter.core.model.Status;
import ru.shift.userimporter.core.model.UserEntity;

import java.io.*;
import java.lang.reflect.Array;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

@UtilityClass
public class FileProcessor {

    private static final int EXPECTED_LINE_ARGS = 6;

    public ProcessingResult processFile(FileEntity fileEntity) throws FileProcessingException {

         ProcessingResult processingResult = new ProcessingResult();

        fileEntity.setProcessingStatus(Status.IN_PROGRESS);
        int lineNumber = 0;
        int invalidRows = 0;
        int validRows = 0;
        String filePath = fileEntity.getStoragePath();

        try(BufferedReader fileReader = new BufferedReader(new FileReader(filePath))){
            String line = fileReader.readLine();
            if(line == null){
                fileEntity.setProcessingStatus(Status.FAILED);
                throw new EmptyFileException("File with hash " + fileEntity.getHash()+
                        "is empty");
            }
            while(line != null){
                lineNumber++;
                UserEntity userEntity = createEntity(line, lineNumber, fileEntity, processingResult);
                if(userEntity == null){
                    invalidRows++;
                }
                else{
                    processingResult.getUserEntities().add(userEntity);
                    validRows++;
                }
                line = fileReader.readLine();
            }
            fileEntity.setProcessingStatus(Status.DONE);
            fileEntity.setProcessedRows(lineNumber);
            fileEntity.setInvalidRows(invalidRows);
            fileEntity.setValidRows(validRows);
            fileEntity.setTotalRows(lineNumber);

            processingResult.setTotalRows(lineNumber);
            processingResult.setValidRows(validRows);
            processingResult.setInvalidRows(invalidRows);
            processingResult.setSuccess(true);

            return processingResult;
        }

        catch(IOException e){
            fileEntity.setProcessingStatus(Status.FAILED);
            throw new FileReadException("error in the process of reading the file " + filePath + e.getMessage());
        }
    }

    //добваить валидацию, если пропущено обязательное поле выкидываем эксепшен с соответствующим описанием
    private UserEntity createEntity(String entityString, int lineNumber, FileEntity fileEntity,
                                    ProcessingResult processingResult){
        String[] entityFields = entityString.split(",", -1);
        ArrayList<Integer> missedFields = findMissedFields(entityFields);
        if(!missedFields.isEmpty()){
            Map<Integer, String> fieldMap = Map.of(
                    0, "lastName",
                    1, "firstName",
                    2, "middleName",
                    3, "email",
                    4, "phone",
                    5, "birthDate"
            );

            StringBuilder errorMessage = new StringBuilder("in line" + lineNumber + "fields: ");
            for(int i = 0; i < missedFields.size(); i++){
                errorMessage.append(fieldMap.get(i + 1)).append(", ");
            }

            errorMessage.append("wasn't found");
            String StringErrorMessage = errorMessage.toString();
            ProcessingError processingError = new ProcessingError(null, fileEntity.getId(),
                    lineNumber, StringErrorMessage, entityString);

            processingResult.getProcessingErrors().add(processingError);

            return null;
        }

        else{

            UserEntity userEntity = new UserEntity();

            userEntity.setLastName(entityFields[0].trim());
            userEntity.setFirstName(entityFields[1].trim());
            userEntity.setMiddleName(entityFields[2].trim());
            userEntity.setEmail(entityFields[3].trim());
            userEntity.setPhone(entityFields[4].trim());
            try{
                userEntity.setBirthDate(LocalDate.parse(entityFields[5]));
            }
            catch(DateTimeException e){
                processingResult.getProcessingErrors().add(ProcessingError.builder().
                        fileId(fileEntity.getId()).
                        rowNumber(lineNumber).
                        errorMessage(e.getMessage()).
                        rawData(entityString).build());
                return null;
            }

            userEntity.setCreatedAt(LocalDate.now());
            userEntity.setUpdatedAt(LocalDate.now());

            return userEntity;
        }
    }

    private ArrayList<Integer> findMissedFields(String[] entityFields){
        ArrayList<Integer> missedIndexes = new ArrayList<>();
        for (int i = 0; i < EXPECTED_LINE_ARGS; i++){
            if(i >= entityFields.length || entityFields[i] == null || entityFields[i].trim().isEmpty()){
                missedIndexes.add(i);
            }
        }
        return missedIndexes;
    }

}
