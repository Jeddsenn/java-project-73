package hexlet.code.service;

import hexlet.code.dto.request.ReqUserDto;
import hexlet.code.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity createNewUser(ReqUserDto userDto);
    UserEntity updateUser(long id, ReqUserDto userDto);
    Optional<UserEntity> getUser(long id);
    List<UserEntity> getAll();
    void deleteUser(long id);


    String getCurrentUserName();

    UserEntity getCurrentUser();
}
