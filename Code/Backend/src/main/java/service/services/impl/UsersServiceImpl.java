package service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.models.Token;
import service.models.User;
import service.repositories.UsersRepository;
import service.services.UsersService;
import service.transfer.UserDto;

import static service.transfer.UserDto.from;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public User findOne(Long userId) {
        return usersRepository.findOne(userId);
    }

    @Override
    public UserDto getUserInfoToIndexPage(Token token) {
        return from((token.getUser()));
    }
}
