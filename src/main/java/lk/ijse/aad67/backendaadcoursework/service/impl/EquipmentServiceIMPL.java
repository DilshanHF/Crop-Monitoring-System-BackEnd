package lk.ijse.aad67.backendaadcoursework.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lk.ijse.aad67.backendaadcoursework.customStatusCode.SelectedErrorStatus;
import lk.ijse.aad67.backendaadcoursework.dao.EquipmentDao;
import lk.ijse.aad67.backendaadcoursework.dao.FieldDao;
import lk.ijse.aad67.backendaadcoursework.dao.StaffDao;
import lk.ijse.aad67.backendaadcoursework.dto.impl.EquipmentDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.entity.impl.EquipmentEntity;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.EquipmentService;
import lk.ijse.aad67.backendaadcoursework.utill.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class EquipmentServiceIMPL implements EquipmentService {

    @Autowired
    private Mapping mapping;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private FieldDao fieldDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveEquipment(EquipmentDto equipmentDto) {
        EquipmentEntity save = equipmentDao.save(mapping.toEquipmentEntity(equipmentDto));
        if ( save  == null) {
            throw new DataPersistException();
        }


    }

    @Override
    public List<EquipmentDto> getEquipmentList() {
        return mapping.asEquipmentDtoList(equipmentDao.findAll());
    }

    @Override
    public Status getEquipmentById(String equipmentId) {
        if (equipmentDao.existsById(equipmentId)) {
            EquipmentEntity equipmentEntity = equipmentDao.getReferenceById(equipmentId);
            return mapping.toEquipmentDto(equipmentEntity);
        }else {
            return new SelectedErrorStatus(2,"Equipment not found");
        }
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDto equipmentDto) {
        Optional<EquipmentEntity> byId = equipmentDao.findById(equipmentId);
        if (byId.isPresent()) {
            byId.get().setEquipmentName(equipmentDto.getEquipmentName());
            byId.get().setEquipmentType(equipmentDto.getEquipmentType());
            byId.get().setEquipmentStatus(equipmentDto.getEquipmentStatus());
            byId.get().setStaffEquipment(staffDao.getReferenceById(equipmentDto.getStaffId()));
            byId.get().setFieldEquipment(fieldDao.getReferenceById(equipmentDto.getFieldCode()));

        }


    }

    @Override
    public void deleteEquipment(String equipmentId) {
        Optional<EquipmentEntity> equipmentEntity = equipmentDao.findById(equipmentId);
        if(!equipmentEntity.isPresent()){
            throw new ItemNotFoundException("User with id " + equipmentId + " not found");
        }else {

            equipmentDao.deleteById(equipmentId);


        }

    }

    @Override
    public String generateEquipmentID() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT c.equipmentId FROM EquipmentEntity c ORDER BY c.equipmentId DESC", String.class);
        query.setMaxResults(1);


        String lastEquipmentId = query.getResultStream().findFirst().orElse(null);

        if (lastEquipmentId != null) {

            int generatedEquipmentId = Integer.parseInt(lastEquipmentId.replace("EQ00-", "")) + 1;
            return String.format("EQ00-%03d", generatedEquipmentId);
        } else {

            return "EQ00-001";
        }
    }
}
