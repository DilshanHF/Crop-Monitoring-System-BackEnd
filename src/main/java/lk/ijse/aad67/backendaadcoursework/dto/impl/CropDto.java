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
public class CropDto implements Status {
    private String cropCode;
    private String cropCategory;
    private String cropCommonName;
    private String cropImage;
    private String cropScientificName;
    private String cropSeason;
    private String logCode;
    private String fieldCode;



}
