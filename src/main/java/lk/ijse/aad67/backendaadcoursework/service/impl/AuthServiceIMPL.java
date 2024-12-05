package lk.ijse.aad67.backendaadcoursework.service.impl;


import lk.ijse.aad67.backendaadcoursework.dao.UserDao;
import lk.ijse.aad67.backendaadcoursework.dto.impl.UserDto;
import lk.ijse.aad67.backendaadcoursework.entity.impl.UserEntity;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.secure.JWTAuthResponse;
import lk.ijse.aad67.backendaadcoursework.secure.SignIn;
import lk.ijse.aad67.backendaadcoursework.service.AuthService;
import lk.ijse.aad67.backendaadcoursework.service.JWTService;
import lk.ijse.aad67.backendaadcoursework.utill.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {
    private final UserDao userDao;

    private final Mapping mapping;

    private final JWTService jwtService;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        System.out.println("AuthServiceIMPL in:"+signIn);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var user = userDao.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(user);
        JWTAuthResponse build = JWTAuthResponse.builder().token(generatedToken).build();
        System.out.println("token printed:"+build);

        return build;
    }





    @Override
    public JWTAuthResponse signUp(UserDto userDTO) {

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity savedUser = userDao.save(mapping.toUserEntity(userDTO));

        if (savedUser == null) {
            throw new DataPersistException();
        }


        var generatedToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(generatedToken).build();
    }


    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        //extract user name
        var userName = jwtService.extractUserName(accessToken);
        //check the user availability in the DB
        var findUser = userDao.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(findUser);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}
