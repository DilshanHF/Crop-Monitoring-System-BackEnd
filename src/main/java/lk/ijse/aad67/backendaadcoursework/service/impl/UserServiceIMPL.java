package lk.ijse.aad67.backendaadcoursework.service.impl;


import jakarta.transaction.Transactional;
import lk.ijse.aad67.backendaadcoursework.customStatusCode.SelectedErrorStatus;
import lk.ijse.aad67.backendaadcoursework.dao.UserDao;
import lk.ijse.aad67.backendaadcoursework.dto.impl.UserDto;
import lk.ijse.aad67.backendaadcoursework.dto.status.Status;
import lk.ijse.aad67.backendaadcoursework.entity.impl.UserEntity;
import lk.ijse.aad67.backendaadcoursework.exception.DataPersistException;
import lk.ijse.aad67.backendaadcoursework.exception.ItemNotFoundException;
import lk.ijse.aad67.backendaadcoursework.exception.NotFoundException;
import lk.ijse.aad67.backendaadcoursework.service.UserService;
import lk.ijse.aad67.backendaadcoursework.utill.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveUser(UserDto UserDto) {
        UserEntity savedUser =
                userDao.save(mapping.toUserEntity(UserDto));
        if (savedUser == null) {
            throw new DataPersistException();
        }
    }

    @Override
    public List<UserDto> getUserList() {
        List<UserEntity> allUsers = userDao.findAll();
        return mapping.asUserDTOList(allUsers);
    }

    @Override
    public Status getUserById(String userId) {
        if (userDao.existsById(userId)) {
            UserEntity selectedUser = userDao.getReferenceById(userId);
            return mapping.toUserDTO(selectedUser);
        }else {
            return new SelectedErrorStatus(2,"user not found");
        }
    }

    @Override
    public void updateUser(String userId, UserDto userDto) {

    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> existedUser = userDao.findById(userId);
        if(!existedUser.isPresent()){
            throw new ItemNotFoundException("User with id " + userId + " not found");
        }else {

            userDao.deleteById(userId);


        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return  userName -> userDao.findByEmail(userName)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
