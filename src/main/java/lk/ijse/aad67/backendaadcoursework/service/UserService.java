package lk.ijse.aad67.backendaadcoursework.service;


import lk.ijse.aad67.backendaadcoursework.dto.impl.UserDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    void saveUser(UserDto UserDto);
    List<UserDto> getUserList();
    Status getUserById(String userId);
    void updateUser(String userId, UserDto userDto);
    void deleteUser(String userId);

    UserDetailsService userDetailsService();
}
