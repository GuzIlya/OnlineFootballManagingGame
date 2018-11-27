package service.services;

import service.models.Token;
import service.models.User;
import service.transfer.UserDto;

public interface UsersService {
    //void signUp(UserForm userForm);
    User findOne(Long userId);
    UserDto getUserInfoToIndexPage(Token token);
}
