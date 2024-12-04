package lk.ijse.aad67.backendaadcoursework.service.impl;

import Final.springBoot.backend.customStatusCode.SelectedErrorStatus;
import Final.springBoot.backend.dao.UserDao;
import Final.springBoot.backend.dto.impl.UserDto;
import Final.springBoot.backend.dto.status.Status;
import Final.springBoot.backend.entity.impl.UserEntity;
import Final.springBoot.backend.exception.DataPersistException;
import Final.springBoot.backend.exception.ItemNotFoundException;
import Final.springBoot.backend.exception.NotFoundException;
import Final.springBoot.backend.service.UserService;
import Final.springBoot.backend.util.Mapping;
import jakarta.transaction.Transactional;
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
