package hexlet.code.service;

import hexlet.code.dto.UserDto;
import hexlet.code.model.UserEntity;

public interface UserService {

    UserEntity createNewUser(UserDto userDto);
    UserEntity updateUser(long id, UserDto userDto);

    String getCurrentUserName();

    UserEntity getCurrentUser();
}
