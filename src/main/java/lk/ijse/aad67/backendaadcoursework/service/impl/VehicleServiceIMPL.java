package lk.ijse.aad67.backendaadcoursework.service.impl;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lk.ijse.aad67.backendaadcoursework.customStatusCode.SelectedErrorStatus;
import lk.ijse.aad67.backendaadcoursework.dao.StaffDao;
import lk.ijse.aad67.backendaadcoursework.dao.VehicleDao;
import lk.ijse.aad67.backendaadcoursework.dto.impl.VehicleDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.entity.impl.VehicleEntity;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.VehicleService;
import lk.ijse.aad67.backendaadcoursework.utill.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleServiceIMPL implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private Mapping mapping;

    @Autowired
    private StaffDao staffDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveVehicle(VehicleDto vehicleDto) {
        System.out.println(vehicleDto);
        VehicleEntity vehicleEntity = mapping.toVehicleEntity(vehicleDto);
        vehicleEntity.setStaffEntity(staffDao.getReferenceById(vehicleDto.getStaffId()));
        VehicleEntity save = vehicleDao.save(vehicleEntity);

        if ( save == null) {
            throw new DataPersistException();
        }
    }

    @Override
    public List<VehicleDto> getVehicleList() {
        return mapping.asVehicleDtoList(vehicleDao.findAll());
    }

    @Override
    public Status getVehicleById(String vehicleId) {
        if (vehicleDao.existsById(vehicleId)) {
            VehicleEntity vehicleEntity = vehicleDao.getReferenceById(vehicleId);
            return mapping.toVehicleDto(vehicleEntity);
        }else {
            return new SelectedErrorStatus(2,"Vehicle not found");
        }
    }

    @Override
    public void updateVehicle(String vehicleId, VehicleDto vehicleDto) {
        System.out.println(vehicleDto);
        Optional<VehicleEntity> byId = vehicleDao.findById(vehicleId);
        if (byId.isPresent()) {
            byId.get().setPlateNumber(vehicleDto.getPlateNumber());
            byId.get().setVehicleCategory(vehicleDto.getVehicleCategory());
            byId.get().setFuelType(vehicleDto.getFuelType());
            byId.get().setVehicleStatus(vehicleDto.getVehicleStatus());
            byId.get().setStaffEntity(staffDao.getReferenceById(vehicleDto.getStaffId()));
            byId.get().setRemarks(vehicleDto.getRemarks());
        }


    }

    @Override
    public void deleteVehicle(String vehicleId) {
        Optional<VehicleEntity> vehicleEntity = vehicleDao.findById(vehicleId);
        if(!vehicleEntity.isPresent()){
            throw new ItemNotFoundException("vehicle with id " + vehicleId + " not found");
        }else {

            vehicleDao.deleteById(vehicleId);


        }
    }

    @Override
    public String generateVehicleID() {
        TypedQuery<String> query = entityManager.createQuery(
                "SELECT c.vehicleCode FROM VehicleEntity c ORDER BY c.vehicleCode DESC", String.class);
        query.setMaxResults(1);


        String lastFieldId = query.getResultStream().findFirst().orElse(null);

        if (lastFieldId != null) {

            int generatedFieldId = Integer.parseInt(lastFieldId.replace("V00-", "")) + 1;
            return String.format("V00-%03d", generatedFieldId);
        } else {

            return "V00-001";
        }
    }
}
