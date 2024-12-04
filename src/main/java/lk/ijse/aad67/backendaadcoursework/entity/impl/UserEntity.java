package lk.ijse.aad67.backendaadcoursework.entity.impl;


import jakarta.persistence.*;
import lk.ijse.aad67.backendaadcoursework.entity.JobRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "userTable")
public class UserEntity {
    @Id
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private JobRole role;


}
