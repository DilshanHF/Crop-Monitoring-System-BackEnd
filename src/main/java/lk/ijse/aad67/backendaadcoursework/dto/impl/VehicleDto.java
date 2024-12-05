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
public class VehicleDto implements Status {
    private String vehicleCode;
    private String plateNumber;
    private String vehicleCategory;
    private String fuelType;
    private String vehicleStatus;
    private String staffId;
    private String remarks;
}
