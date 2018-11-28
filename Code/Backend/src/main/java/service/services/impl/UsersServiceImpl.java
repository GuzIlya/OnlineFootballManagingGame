package service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.models.Player;
import service.models.Token;
import service.models.User;
import service.repositories.PlayersRepository;
import service.repositories.UsersRepository;
import service.services.UsersService;
import service.transfer.UserDto;

import java.util.ArrayList;
import java.util.List;

import static service.transfer.UserDto.from;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PlayersRepository playersRepository;

    @Override
    public User findOne(Long userId) {
        return usersRepository.findOne(userId);
    }

    @Override
    public UserDto getUserInfoToIndexPage(Token token) {
        return from((token.getUser()));
    }

    @Override
    public void buyPlayer(Token token, String name) {
        Player playerToBuy = playersRepository.findByName(name);
        if(playerToBuy == null)
            throw new IllegalArgumentException("Player not found");

        User buyerUser = token.getUser();
        if(buyerUser.getBalance() < playerToBuy.getCost())
            throw new IllegalArgumentException("Not enough money");
        else {
            buyerUser.getPlayers().add(playerToBuy);
            playerToBuy.getOwners().add(buyerUser);

            buyerUser.setBalance(buyerUser.getBalance() - playerToBuy.getCost());

            usersRepository.save(buyerUser);
            playersRepository.save(playerToBuy);
        }
    }

    @Override
    public List<String> getPlayersAvailable(Token token, String position) {
        User buyerUser = token.getUser();
        List<String> playerNamesList = new ArrayList<>();
        for (Player playerToCheck: buyerUser.getPlayers()){
            if (playerToCheck.getPosition().equals(position)){
                playerNamesList.add(playerToCheck.getName());
            }
        }

        if(playerNamesList.isEmpty())
            throw new IllegalArgumentException("No players found for this position");

        return playerNamesList;
    }
}
