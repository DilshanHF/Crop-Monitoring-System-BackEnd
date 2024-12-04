package lk.ijse.aad67.backendaadcoursework.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicleTable")
public class VehicleEntity {
    @Id
    private String vehicleCode;
    private String plateNumber;
    private String vehicleCategory;
    private String FuelType;
    private String vehicleStatus;

    @ManyToOne
    @JoinColumn(name = "staffId",nullable = false)
    private StaffEntity staffEntity;

    private String remarks;
}
