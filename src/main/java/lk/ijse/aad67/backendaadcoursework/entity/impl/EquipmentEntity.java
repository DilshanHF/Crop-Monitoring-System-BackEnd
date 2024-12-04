package lk.ijse.aad67.backendaadcoursework.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "equipmentTable")
public class EquipmentEntity {
    @Id
    private String equipmentId;
    private String equipmentName;
    private String equipmentType;
    private String equipmentStatus;
    @ManyToOne
    @JoinColumn(name = "staffId",nullable = false  )
    private StaffEntity staffEquipment;
    @ManyToOne
    @JoinColumn(name = "fieldCode",nullable = false  )
    private FieldEntity fieldEquipment;
}
