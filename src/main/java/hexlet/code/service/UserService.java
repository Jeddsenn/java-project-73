package hexlet.code.service;

import hexlet.code.dto.UserDto;
import hexlet.code.model.UserEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEntity createNewUser(UserDto userDto);
    UserEntity updateUser(long id, UserDto userDto);
    Optional<UserEntity> getUser(long id);
    List<UserEntity> getAll();
    void deleteUser(long id);


    String getCurrentUserName();

    UserEntity getCurrentUser();
}
