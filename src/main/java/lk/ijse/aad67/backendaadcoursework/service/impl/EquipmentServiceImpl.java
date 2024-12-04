package lk.ijse.aad67.backendaadcoursework.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.aad67.backendaadcoursework.dto.impl.EquipmentDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.service.EquipmentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
    @Override
    public void saveEquipment(EquipmentDto equipmentDto) {

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
