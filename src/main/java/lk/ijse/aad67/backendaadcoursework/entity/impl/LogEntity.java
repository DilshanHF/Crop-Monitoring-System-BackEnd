package lk.ijse.aad67.backendaadcoursework.entity.impl;


import jakarta.persistence.*;
import lk.ijse.aad67.backendaadcoursework.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "logTable")
public class LogEntity implements SuperEntity {
    @Id
    private String logCode;
    private Date logdate;
    private String LogDetails;

    @Column(columnDefinition = "LONGTEXT")
    private String ObservedImage;

    @OneToMany(mappedBy = "log")
    private List<StaffEntity> staff;

    @OneToMany(mappedBy = "logField")
    private List<FieldEntity> logField;

    @OneToMany(mappedBy = "log")
    private List<CropEntity> cropEntitiesCrop;



}
