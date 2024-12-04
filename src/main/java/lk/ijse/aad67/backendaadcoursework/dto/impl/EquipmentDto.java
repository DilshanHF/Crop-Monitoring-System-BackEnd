package lk.ijse.aad67.backendaadcoursework.dto.impl;


import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EquipmentDto implements Status {
    private String equipmentId;
    private String equipmentName;
    private String equipmentType;
    private String equipmentStatus;
    private String staffId;
    private String fieldCode;
}
