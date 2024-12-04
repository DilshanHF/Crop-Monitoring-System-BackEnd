package lk.ijse.aad67.backendaadcoursework.service;



import lk.ijse.aad67.backendaadcoursework.dto.impl.EquipmentDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(EquipmentDto equipmentDto);
    List<EquipmentDto> getEquipmentList();
    Status getEquipmentById(String cropId);
    void updateEquipment(String equipmentId, EquipmentDto equipmentDto);
    void deleteEquipment(String equipmentId);
    String generateEquipmentID();
}
