package lk.ijse.aad67.backendaadcoursework.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lk.ijse.aad67.backendaadcoursework.customStatusCode.SelectedErrorStatus;
import lk.ijse.aad67.backendaadcoursework.dao.LogDao;
import lk.ijse.aad67.backendaadcoursework.dto.impl.LogDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.entity.impl.LogEntity;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.LogService;
import lk.ijse.aad67.backendaadcoursework.utill.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogServiceIMPl implements LogService {

    @Autowired
    private LogDao logDao;

    @Autowired
    private Mapping mapping;

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public void saveLog(LogDto logDto) {
        LogEntity save = logDao.save(mapping.toLogEntity(logDto));

        if ( save == null) {
            throw new DataPersistException();
        }
    }

    @Override
    public List<LogDto> getLogList() {
        return mapping.asLogDtoList(logDao.findAll());
    }

    @Override
    public Status getLogById(String logId) {
        if (logDao.existsById(logId)) {
            LogEntity logEntity = logDao.getOne(logId);
            return mapping.toLogDto(logEntity);
        }else {
            return new SelectedErrorStatus(2,"Field not found");
        }
    }

    @Override
    public void updateLog(String logId, LogDto logDto) {
        Optional<LogEntity> byId = logDao.findById(logId);
        if (byId.isPresent()) {
            byId.get().setLogDetails(logDto.getLogDetails());
            byId.get().setObservedImage(logDto.getObservedImage());
            byId.get().setLogdate(Date.valueOf(logDto.getLogDate()));
        }
    }

    @Override
    public void deleteLog(String logId) {
        Optional<LogEntity> byId = logDao.findById(logId);
        if (!byId.isPresent()) {
            throw new ItemNotFoundException("User with id " + logId + " not found");
        }
        logDao.deleteById(logId);
    }

    @Override
    public String generateLogID() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT c.logCode FROM LogEntity c ORDER BY c.logCode DESC", String.class);
        query.setMaxResults(1);


        String lastFieldId = query.getResultStream().findFirst().orElse(null);

        if (lastFieldId != null) {

            int generatedFieldId = Integer.parseInt(lastFieldId.replace("L00-", "")) + 1;
            return String.format("L00-%03d", generatedFieldId);
        } else {

            return "L00-001";
        }
    }
}
