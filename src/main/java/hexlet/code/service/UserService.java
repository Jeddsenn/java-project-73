package hexlet.code.service;

import hexlet.code.dto.request.UserReq;
import hexlet.code.dto.response.UserRes;
import hexlet.code.model.UserEntity;
import java.util.List;

public interface UserService {

    UserRes createNewUser(UserReq userDto);
    UserRes updateUser(long id, UserReq userDto);
    UserRes getUser(long id);
    List<UserRes> getAll();
    void deleteUser(long id);


    String getCurrentUserName();

    UserEntity getCurrentUser();
}
