package lk.ijse.aad67.backendaadcoursework.service;



import lk.ijse.aad67.backendaadcoursework.dto.impl.LogDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;

import java.util.List;

public interface LogService {
    void saveLog(LogDto logDto);
    List<LogDto> getLogList();
    Status getLogById(String logId);
    void updateLog(String logId, LogDto logDto);
    void deleteLog(String logId);
    String generateLogID();
}
