package lk.ijse.aad67.backendaadcoursework.service;



import lk.ijse.aad67.backendaadcoursework.dto.impl.FieldDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;

import java.util.List;

public interface FieldService {
    void saveField(FieldDto fieldDto);
    List<FieldDto> getFieldList() throws Exception;
    Status getFieldById(String fieldId) throws Exception;
    void updateField(String fieldId,FieldDto FieldDto);
    void deleteField(String fieldId);
    String generateFieldID();
}
