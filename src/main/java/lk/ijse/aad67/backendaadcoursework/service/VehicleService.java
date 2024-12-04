package lk.ijse.aad67.backendaadcoursework.service;



import lk.ijse.aad67.backendaadcoursework.dto.impl.VehicleDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDto vehicleDto);
    List<VehicleDto> getVehicleList();
    Status getVehicleById(String vehicleId);
    void updateVehicle(String vehicleId, VehicleDto vehicleDto);
    void deleteVehicle(String vehicleId);

    String generateVehicleID();
}
