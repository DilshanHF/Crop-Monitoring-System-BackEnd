package lk.ijse.aad67.backendaadcoursework.dto.impl;


import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldDto  implements Status {
    private String fieldCode;
    private String fieldName;
    private int fieldLocation;
    private Double fieldSize;
    private List<String> crops;
    private List<String> staffField;
    private String image1;
    private String image2;
    private String logCode;

}
