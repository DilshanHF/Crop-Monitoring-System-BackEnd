package lk.ijse.aad67.backendaadcoursework.entity.impl;


import jakarta.persistence.*;
import lk.ijse.aad67.backendaadcoursework.entity.Gender;
import lk.ijse.aad67.backendaadcoursework.entity.JobDesignation;
import lk.ijse.aad67.backendaadcoursework.entity.JobRole;
import lk.ijse.aad67.backendaadcoursework.entity.SuperEntity;
import lk.ijse.aad67.backendaadcoursework.entity.composite.Address;
import lk.ijse.aad67.backendaadcoursework.entity.composite.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "staffTable")
public class StaffEntity implements SuperEntity {
    @Id
    private String staffId;
    @Embedded
    private Name name;
    @Enumerated(EnumType.STRING)
    private JobDesignation staffDesignation;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Date joinedDate;
    private Date DOB;
    @Embedded
    private Address address;

    private String contact;
    private String email;

    @Enumerated(EnumType.STRING)
    private JobRole jobRole;

    /*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "staffField",
            joinColumns = @JoinColumn(name = "staffId"),
            inverseJoinColumns = @JoinColumn(name = "staffHave")
    )
    private List<FieldEntity> fieldEntity;*/

/*    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "staff_field",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id")
    )
    private List<FieldEntity> fieldEntity = new ArrayList<>();*/

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "staff_field",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id")
    )
    private List<FieldEntity> fieldsAssigned = new ArrayList<>();

    @OneToMany(mappedBy = "staffEntity")
    private List<VehicleEntity> vehicleEntity;

    @OneToMany(mappedBy = "staffEquipment")
    private List<EquipmentEntity> staffEquipment;

    @Column(columnDefinition = "LONGTEXT")
    private String image;

    @ManyToOne
    @JoinColumn(name = "logCode",nullable = false)
    private LogEntity log;
  /*  @ManyToOne
    @JoinColumn(name = "fieldCode",nullable = false)
    private FieldEntity staff;*/
}
