package lk.ijse.aad67.backendaadcoursework.entity.impl;


import jakarta.persistence.*;
import lk.ijse.aad67.backendaadcoursework.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cropTable")
public class CropEntity implements SuperEntity {
    @Id
    private String cropCode;
    private String cropCommonName;
    private String cropScientificName;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;
    private String cropCategory;
    private String cropSeason;
    @ManyToOne
    @JoinColumn(name = "logCode", nullable = false)
    private LogEntity log;
    @ManyToOne
    @JoinColumn(name = "fieldId",nullable = false)
    private FieldEntity field;

}
