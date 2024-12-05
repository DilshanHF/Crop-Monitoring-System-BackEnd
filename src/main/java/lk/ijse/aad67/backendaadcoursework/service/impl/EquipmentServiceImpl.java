package lk.ijse.aad67.backendaadcoursework.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.aad67.backendaadcoursework.dao.EquipmentDao;
import lk.ijse.aad67.backendaadcoursework.dto.impl.EquipmentDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.entity.impl.EquipmentEntity;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.service.EquipmentService;
import lk.ijse.aad67.backendaadcoursework.utill.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveEquipment(EquipmentDto equipmentDto) {
        System.out.println(equipmentDto);
        EquipmentEntity save = equipmentDao.save(mapping.toEquipmentEntity(equipmentDto));
        if ( save  == null) {
            throw new DataPersistException();
        }
    }

    @Override
    public List<EquipmentDto> getEquipmentList() {
        return List.of();
    }

    @Override
    public Status getEquipmentById(String cropId) {
        return null;
    }

    @Override
    public void updateEquipment(String equipmentId, EquipmentDto equipmentDto) {

    }

    @Override
    public void deleteEquipment(String equipmentId) {

    }

    @Override
    public String generateEquipmentID() {
        return "";
    }
}
