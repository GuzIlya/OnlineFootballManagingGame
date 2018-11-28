package service.services;

import service.models.Token;
import service.models.User;
import service.transfer.UserDto;

import java.util.List;

public interface UsersService {
    //void signUp(UserForm userForm);
    User findOne(Long userId);
    UserDto getUserInfoToIndexPage(Token token);
    void buyPlayer(Token token, String name);
    List<String> getPlayersAvailable(Token token, String position);
}
