package lk.ijse.aad67.backendaadcoursework.controllers;

import jakarta.persistence.PersistenceContext;
import lk.ijse.aad67.backendaadcoursework.dto.impl.LogDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.LogService;
import lk.ijse.aad67.backendaadcoursework.utill.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PutMapping(value = "/{logId}")
    public ResponseEntity<Void> updateLog(
            @PathVariable("logId") String logId,
            @RequestPart ("date") String date,
            @RequestPart ("LogDetails") String logDetails,
            @RequestPart ("ObservedImage") MultipartFile observedImage){

        try {

            logService.updateLog(logId,convertLogToDto(logId,date,logDetails,observedImage));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LogDto> getALlLogs(){
        return logService.getLogList();
    }

    @GetMapping(value = "/{logId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Status getSelectedLog(@PathVariable ("logId") String logId){
        return logService.getLogById(logId);
    }

    @DeleteMapping(value = "/{logId}")
    public ResponseEntity<Object> deleteEquipment(@PathVariable("logId") String logId){
        try {
            logService.deleteLog(logId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genLogID")
    public String generateLogId(){
        return logService.generateLogID();
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
