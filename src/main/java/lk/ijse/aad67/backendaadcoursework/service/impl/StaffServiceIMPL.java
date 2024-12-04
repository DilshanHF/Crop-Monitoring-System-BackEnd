package lk.ijse.aad67.backendaadcoursework.service.impl;

import Final.springBoot.backend.customStatusCode.SelectedErrorStatus;
import Final.springBoot.backend.dao.FieldDao;
import Final.springBoot.backend.dao.LogDao;
import Final.springBoot.backend.dao.StaffDao;
import Final.springBoot.backend.dto.impl.StaffDto;
import Final.springBoot.backend.dto.status.Status;
import Final.springBoot.backend.entity.composite.Address;
import Final.springBoot.backend.entity.composite.Name;
import Final.springBoot.backend.entity.impl.FieldEntity;
import Final.springBoot.backend.entity.impl.StaffEntity;
import Final.springBoot.backend.exception.DataPersistException;
import Final.springBoot.backend.exception.ItemNotFoundException;
import Final.springBoot.backend.service.StaffService;
import Final.springBoot.backend.util.Mapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StaffServiceIMPL implements StaffService {

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private Mapping mapping;

    @Autowired
    private LogDao logDao;

    @Autowired
    private FieldDao fieldDao;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void saveStaff(StaffDto staffDto) {
        StaffEntity staffToSave = mapping.toStaffEntity(staffDto);

        List<String> fieldIds = staffDto.getFields().stream()
                .map(fieldId -> fieldDao.getReferenceById(fieldId).getFieldCode())
                .collect(Collectors.toList());

        staffToSave.getFieldsAssigned().clear();


        for (String fieldId : fieldIds) {
            FieldEntity fieldEntity = fieldDao.findById(fieldId).orElseThrow(() -> new ItemNotFoundException("Field not found: " + fieldId));
            staffToSave.getFieldsAssigned().add(fieldEntity);
            fieldEntity.getStaffAssigned().add(staffToSave);
        }

        StaffEntity savedStaff = staffDao.save(staffToSave);

        if (savedStaff == null) {
            throw new DataPersistException();
        }
    }



    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<StaffDto> getStaffList()  {
        List<StaffEntity> staffEntities = staffDao.findAll();

        List<StaffDto> staffDtos = new ArrayList<>();

        for (StaffEntity staffEntity : staffEntities) {
            staffDtos.add(convertStaffEntityToDto(staffEntity));
        }
        return staffDtos;
    }

    private StaffDto convertStaffEntityToDto(StaffEntity staffEntity) {
        StaffDto staffDto = new StaffDto();

        staffDto.setStaffId(staffEntity.getStaffId());
        staffDto.setFirstName(staffEntity.getName().getFirstName());
        staffDto.setLastName(staffEntity.getName().getLastName());
        staffDto.setStaffDesignation(staffEntity.getStaffDesignation());
        staffDto.setGender(staffEntity.getGender());
        staffDto.setJoinedDate(staffEntity.getJoinedDate().toString());
        staffDto.setAddressLine01(staffEntity.getAddress().getAddressLine01());
        staffDto.setAddressLine02(staffEntity.getAddress().getAddressLine02());
        staffDto.setAddressLine03(staffEntity.getAddress().getAddressLine03());
        staffDto.setAddressLine04(staffEntity.getAddress().getAddressLine04());
        staffDto.setAddressLine05(staffEntity.getAddress().getAddressLine05());
        staffDto.setContact(staffEntity.getContact());
        staffDto.setEmail(staffEntity.getEmail());
        staffDto.setJobRole(staffEntity.getJobRole());
        staffDto.setImage(staffEntity.getImage());
        staffDto.setLogCode(staffEntity.getLog().getLogCode());
        List<String> fieldList = new ArrayList<>();
        for (FieldEntity field : staffEntity.getFieldsAssigned()) {
            fieldList.add(field.getFieldCode());
        }
        if (fieldList.isEmpty()){
            fieldList.add("No Assign Field IDS");
        }
        staffDto.setFields(fieldList);
        return staffDto;
    }

   /* @Override
    public List<StaffDto> getStaffList() {
        return mapping.adStaffDtoList(staffDao.findAll());
    }*/

    @Override
    public Status getStaffById(String staffId) {
        if (staffDao.existsById(staffId)) {
           return convertStaffEntityToDto(staffDao.getReferenceById(staffId));
        }else {
            return new SelectedErrorStatus(2,"user not found");
        }
    }

    @Override
    public void updateStaff(String staffId, StaffDto staffDto) {
        Optional<StaffEntity> byId = staffDao.findById(staffId);

        if (!byId.isPresent()) {
            throw new ItemNotFoundException("Staff not found");
        }

        if (byId.isPresent()) {
            byId.get().setName(new Name(staffDto.getFirstName(), staffDto.getLastName()));
            byId.get().setStaffDesignation(staffDto.getStaffDesignation());
            byId.get().setGender(staffDto.getGender());
            byId.get().setJoinedDate(Date.valueOf(staffDto.getJoinedDate()));
            byId.get().setDOB(Date.valueOf(staffDto.getDOB()));
            byId.get().setAddress(new Address(staffDto.getAddressLine01(),staffDto.getAddressLine02(),staffDto.getAddressLine03(),staffDto.getAddressLine04(),staffDto.getAddressLine05()));
            byId.get().setContact(staffDto.getContact());
            byId.get().setEmail(staffDto.getEmail());
            byId.get().setJobRole(staffDto.getJobRole());
            byId.get().setImage(staffDto.getImage());
            byId.get().setLog(logDao.getReferenceById(staffDto.getLogCode()));
        }
    }

    @Override
    public void deleteStaff(String staffId) {
        Optional<StaffEntity> byId = staffDao.findById(staffId);
        if (!byId.isPresent()) {
            throw new ItemNotFoundException("User with id " + staffId + " not found");
        }
        staffDao.deleteById(staffId);
    }

    @Override
    public String generateStaffID() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT c.staffId FROM StaffEntity c ORDER BY c.staffId DESC", String.class);
        query.setMaxResults(1);


        String lastFieldId = query.getResultStream().findFirst().orElse(null);

        if (lastFieldId != null) {

            int generatedFieldId = Integer.parseInt(lastFieldId.replace("S00-", "")) + 1;
            return String.format("S00-%03d", generatedFieldId);
        } else {

            return "S00-001";
        }
    }
}
