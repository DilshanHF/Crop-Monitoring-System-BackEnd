package lk.ijse.aad67.backendaadcoursework.entity.composite;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String AddressLine01;
    private String AddressLine02;
    private String AddressLine03;
    private String AddressLine04;
    private String AddressLine05;
}
