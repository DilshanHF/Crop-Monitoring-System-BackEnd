package lk.ijse.aad67.backendaadcoursework.controllers;

import jakarta.persistence.PersistenceContext;
import lk.ijse.aad67.backendaadcoursework.dto.impl.LogDto;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.service.LogService;
import lk.ijse.aad67.backendaadcoursework.utill.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/log")
public class LogController {
    @Autowired
    private LogService logService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveLog(
            @RequestPart("logCode") String logCode,
            @RequestPart ("date") String date,
            @RequestPart ("LogDetails") String logDetails,
            @RequestPart ("ObservedImage") MultipartFile observedImage
    ){
        try {

            logService.saveLog(convertLogToDto(logCode,date,logDetails,observedImage));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private LogDto convertLogToDto(String logCode, String logDate, String logDetails, MultipartFile observedImage) throws IOException {
        LogDto logDto1 = new LogDto();
        logDto1.setLogCode(logCode);
        logDto1.setLogDate(logDate);
        logDto1.setLogDetails(logDetails);
        logDto1.setObservedImage(AppUtil.convertImage(observedImage));
        return logDto1;
    }
}
