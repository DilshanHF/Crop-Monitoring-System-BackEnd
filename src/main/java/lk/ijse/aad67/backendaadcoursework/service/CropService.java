package lk.ijse.aad67.backendaadcoursework.service;



import lk.ijse.aad67.backendaadcoursework.dto.impl.CropDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;

import java.util.List;

public interface CropService {
    void saveCrop(CropDto cropDto);
    List<CropDto> getCropList();
    Status getCropById(String cropId);
    void updateCrop(String cropId, CropDto cropDto);
    void deleteCrop(String cropId);
    String generateCropID();
}
