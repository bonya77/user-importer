package ru.shift.userimporter.core.util;

import ru.shift.userimporter.core.exception.FileProcessingException;
import ru.shift.userimporter.core.model.FileEntity;
import ru.shift.userimporter.core.model.ProcessingError;
import ru.shift.userimporter.core.model.Status;
import ru.shift.userimporter.core.model.UserEntity;

import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.nio.Buffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileProcessor {
    private final List<ProcessingError> processingErrors = new ArrayList<>();
    private final List<UserEntity> userEntities = new ArrayList<>();

    List<ProcessingError> getProcessingErrors(){
        return processingErrors;
    }

    //сделать валидацию
    public void processFile(FileEntity fileEntity) throws Exception {

        fileEntity.setProcessingStatus(Status.IN_PROGRESS);
        int lineNumber = 0;
        int invalidRows = 0;
        String filePath = fileEntity.getStoragePath();

        try(BufferedReader fileReader = new BufferedReader(new FileReader(filePath))){
            String line = fileReader.readLine();
            if(line == null){
                fileEntity.setProcessingStatus(Status.FAILED);
                throw new FileProcessingException("File with hash " + fileEntity.getHash()+
                        "is empty");
            }
            while(line != null){
                lineNumber++;
                UserEntity userEntity = createEntity(line, lineNumber, fileEntity);
                if(userEntity == null){
                    line = fileReader.readLine();
                    invalidRows += 1;
                    continue;
                }
                userEntities.add(userEntity);
                line = fileReader.readLine();
            }
            fileEntity.setProcessingStatus(Status.DONE);
            fileEntity.setProcessedRows(lineNumber - invalidRows);
            fileEntity.setInvalidRows(invalidRows);
            fileEntity.setTotalRows(lineNumber);
        }

        catch(IOException e){
            fileEntity.setProcessingStatus(Status.FAILED);
            throw new FileProcessingException("error in the process of reading the file");
        }
    }

    //добваить валидацию, если пропущено обязательное поле выкидываем эксепшен с соответствующим описанием
    public UserEntity createEntity(String entityString, int lineNumber, FileEntity fileEntity){
        String[] entityFields = entityString.split(",");
        if(isFieldMissed(entityFields)){
            Map<Integer, String> fieldMap = Map.of(
                    0, "lastName",
                    1, "firstName",
                    2, "middleName",
                    3, "email",
                    4, "phone",
                    5, "birthDate"
            );

            StringBuilder errorMessage = new StringBuilder("in line" + lineNumber + "lines: ");
            for(int i = 0; i < 6; i++){
                if(entityFields[i] == null || entityFields[i].trim().isEmpty()){
                    errorMessage.append(fieldMap.get(i)).append(", ");
                }

            }
            errorMessage.append("wasnt found");
            String StringErrorMessage = errorMessage.toString();
            ProcessingError processingError = new ProcessingError(null, fileEntity.getId(),
                    lineNumber, StringErrorMessage, entityString);

            processingErrors.add(processingError);

            return null;
        }

        else{

            UserEntity userEntity = new UserEntity();

            userEntity.setLastName(entityFields[0].trim());
            userEntity.setFirstName(entityFields[1].trim());
            userEntity.setMiddleName(entityFields[2].trim());
            userEntity.setEmail(entityFields[3].trim());
            userEntity.setPhone(entityFields[4].trim());
            userEntity.setBirthDate(LocalDate.parse(entityFields[5]));
            userEntity.setCreatedAt(LocalDate.now());
            userEntity.setUpdatedAt(LocalDate.now());

            return userEntity;
        }
    }

    boolean isFieldMissed(String[] entityFields){
        for(String str : entityFields){
            if(str == null || str.trim().isEmpty()){
                return true;
            }
        }
        return false;
    }



}
