package lk.ijse.aad67.backendaadcoursework.secure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SignIn {
    private String email;
    private String password;
}
