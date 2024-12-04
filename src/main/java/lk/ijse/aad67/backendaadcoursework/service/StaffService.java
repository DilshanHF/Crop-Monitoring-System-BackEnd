package lk.ijse.aad67.backendaadcoursework.service;



import lk.ijse.aad67.backendaadcoursework.dto.impl.StaffDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDto staffDto);
    List<StaffDto> getStaffList();
    Status getStaffById(String staffId);
    void updateStaff(String staffId, StaffDto staffDto);
    void deleteStaff(String staffId);

    String generateStaffID();
}
