package lk.ijse.aad67.backendaadcoursework.customStatusCode;



import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedErrorStatus implements Status {

    private int errorCode;
    private String errorMessage;

}
