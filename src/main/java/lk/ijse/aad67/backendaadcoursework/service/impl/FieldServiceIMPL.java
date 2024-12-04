package lk.ijse.aad67.backendaadcoursework.service.impl;


import Final.springBoot.backend.customStatusCode.SelectedErrorStatus;
import Final.springBoot.backend.dao.FieldDao;
import Final.springBoot.backend.dao.LogDao;
import Final.springBoot.backend.dao.StaffDao;
import Final.springBoot.backend.dto.impl.FieldDto;
import Final.springBoot.backend.dto.status.Status;
import Final.springBoot.backend.entity.impl.FieldEntity;
import Final.springBoot.backend.entity.impl.StaffEntity;
import Final.springBoot.backend.exception.DataPersistException;
import Final.springBoot.backend.exception.ItemNotFoundException;
import Final.springBoot.backend.service.FieldService;
import Final.springBoot.backend.util.Mapping;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Transactional
@Service
public class FieldServiceIMPL implements FieldService {

    @Autowired
    private FieldDao fieldDao;

    @Autowired
    private LogDao logDao;

    @Autowired
    private Mapping mapping;

    @Autowired
    private StaffDao staffDao;

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public void saveField(FieldDto fieldDto) {
        FieldEntity fieldToSave = mapping.toFieldEntity(fieldDto);

        List<String> staffIds = fieldDto.getStaffField().stream()
                .map(staffId -> fieldDao.getReferenceById(staffId).getFieldCode())
                .collect(Collectors.toList());

        fieldToSave.getStaffAssigned().clear();


        for (String staffId : staffIds) {
            StaffEntity staffEntity = staffDao.findById(staffId).orElseThrow(() -> new ItemNotFoundException("Staff not found: " + staffId));
            fieldToSave.getStaffAssigned().add(staffEntity);
            staffEntity.getFieldsAssigned().add(fieldToSave);
        }

        FieldEntity savedField = fieldDao.save(fieldToSave);

        if (savedField == null) {
            throw new DataPersistException();
        }
    }


  /*  @Override
    public void saveField(FieldDto fieldDto) {

        FieldEntity save = fieldDao.save(mapping.toFieldEntity(fieldDto));

        if ( save == null) {
            throw new DataPersistException();
        }

    }*/


    public List<FieldDto> getFieldList() throws Exception {
        List<FieldEntity> fieldEntities = fieldDao.findAll();

        List<FieldDto> fieldDtos = new ArrayList<>();
        for (FieldEntity fieldEntity : fieldEntities) {
            fieldDtos.add(convertFieldEntityToDto(fieldEntity));
        }
        return fieldDtos;
    }

    private FieldDto convertFieldEntityToDto(FieldEntity fieldEntity) throws Exception {
        FieldDto fieldDto = new FieldDto();

        fieldDto.setFieldCode(fieldEntity.getFieldCode());
        fieldDto.setFieldName(fieldEntity.getFieldName());
        fieldDto.setFieldLocation(fieldEntity.getFieldLocation());
        fieldDto.setFieldSize(fieldEntity.getFieldSize());

        fieldDto.setCrops(null);
        List<String> staffField = new ArrayList<>();
        for (StaffEntity staff : fieldEntity.getStaffAssigned()) {
            staffField.add(staff.getStaffId());
        }
        if (staffField.isEmpty()) {
            staffField.add("No Assign staff Ids");
        }
        fieldDto.setStaffField(staffField);

        fieldDto.setImage1(fieldEntity.getImage1());
        fieldDto.setImage2(fieldEntity.getImage2());
        fieldDto.setLogCode(fieldEntity.getLogField().getLogCode());

        return fieldDto;
    }


 /*   @Override
    public List<FieldDto> getFieldList() {
            return mapping.asFieldDtoList(fieldDao.findAll());
    }*/

    @Override
    public Status getFieldById(String fieldId) throws Exception {
        if (fieldDao.existsById(fieldId)) {
            return convertFieldEntityToDto(fieldDao.getReferenceById(fieldId));
        }else {
            return new SelectedErrorStatus(2,"Field not found");
        }
    }

    @Override
    public void updateField(String fieldId, FieldDto FieldDto) {
        Optional<FieldEntity> byId = fieldDao.findById(fieldId);
        if (byId.isPresent()) {
            byId.get().setFieldName(FieldDto.getFieldName());
            byId.get().setFieldLocation(FieldDto.getFieldLocation());
            byId.get().setFieldSize(FieldDto.getFieldSize());
            byId.get().setImage1(FieldDto.getImage1());
            byId.get().setImage2(FieldDto.getImage2());
            byId.get().setLogField(logDao.getReferenceById(FieldDto.getLogCode()));
        }
    }

    @Override
    public void deleteField(String fieldId) {

        Optional<FieldEntity> byId = fieldDao.findById(fieldId);
        if (!byId.isPresent()) {
            throw new ItemNotFoundException("User with id " + fieldId + " not found");
        }
        fieldDao.deleteById(fieldId);

    }



    @Override
    public String generateFieldID() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT c.fieldCode FROM FieldEntity c ORDER BY c.fieldCode DESC", String.class);
        query.setMaxResults(1);


        String lastFieldId = query.getResultStream().findFirst().orElse(null);

        if (lastFieldId != null) {

            int generatedFieldId = Integer.parseInt(lastFieldId.replace("F00-", "")) + 1;
            return String.format("F00-%03d", generatedFieldId);
        } else {

            return "F00-001";
        }
    }


}
