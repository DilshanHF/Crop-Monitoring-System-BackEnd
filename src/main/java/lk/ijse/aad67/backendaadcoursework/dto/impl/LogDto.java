package lk.ijse.aad67.backendaadcoursework.dto.impl;



import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDto implements Status {
    private String logCode;
    private String logDate;
    private String LogDetails;
    private String ObservedImage;
    private List<FieldDto> fieldCode;
    private List<CropDto> cropCode;
    private List<StaffDto> staffId;
}
