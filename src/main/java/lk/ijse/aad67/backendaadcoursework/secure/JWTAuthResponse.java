package lk.ijse.aad67.backendaadcoursework.secure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JWTAuthResponse {
    private String token;
}
