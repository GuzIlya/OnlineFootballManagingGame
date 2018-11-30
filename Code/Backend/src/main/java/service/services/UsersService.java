package service.services;

import service.models.Token;
import service.models.User;
import service.transfer.MatchResultDto;
import service.transfer.UserDto;
import service.transfer.UserRateDto;

import java.util.List;

public interface UsersService {
    //void signUp(UserForm userForm);
    User findOne(Long userId);
    UserDto getUserInfoToIndexPage(Token token);
    void buyPlayer(Token token, String name);
    List<String> getPlayersAvailable(Token token, String position);
    void addTeam(Token token, List<String> playerNames);
    List<UserRateDto> getUsersRate();
    List<MatchResultDto> getResults();
}
