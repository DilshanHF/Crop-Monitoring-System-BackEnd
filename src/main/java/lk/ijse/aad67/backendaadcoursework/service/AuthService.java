package lk.ijse.aad67.backendaadcoursework.service;


import lk.ijse.aad67.backendaadcoursework.dto.impl.UserDto;
import lk.ijse.aad67.backendaadcoursework.secure.JWTAuthResponse;
import lk.ijse.aad67.backendaadcoursework.secure.SignIn;

public interface AuthService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(UserDto userDTO);
    JWTAuthResponse refreshToken(String refreshToken);
}
