package service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.models.*;
import service.repositories.MatchResultRepository;
import service.repositories.PlayersRepository;
import service.repositories.TeamsRepository;
import service.repositories.UsersRepository;
import service.services.UsersService;
import service.transfer.MatchResultDto;
import service.transfer.UserDto;
import service.transfer.UserRateDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static service.transfer.UserDto.from;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PlayersRepository playersRepository;

    @Autowired
    private TeamsRepository teamsRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

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

    @Override
    public void addTeam(Token token, List<String> playerNames) {
        User potentialOwner = token.getUser();

        Optional<UsersTeam> oldTeam = teamsRepository.findByOwner(potentialOwner);
        if(oldTeam.isPresent()){
            for(Player oldPlayer: oldTeam.get().getPlayers()){
                oldPlayer.getTeams().remove(oldTeam.get());
            }
            oldTeam.get().getPlayers().removeAll(oldTeam.get().getPlayers());

            teamsRepository.delete(oldTeam.get());
        }

        UsersTeam team = new UsersTeam();
        potentialOwner.setTeamOwned(team);

        List<Player> players = new ArrayList<>();
        for(String playerName: playerNames){
            Player playerToAdd = playersRepository.findByName(playerName);
            playerToAdd.getTeams().add(team);
            players.add(playerToAdd);
        }

        team.setOwner(potentialOwner);
        team.setPlayers(players);

        teamsRepository.save(team);
    }

    @Override
    public List<MatchResultDto> getResults() {
        return MatchResultDto.from(matchResultRepository.findAll());
    }

    @Override
    public List<UserRateDto> getUsersRate() {
        return UserRateDto.from(usersRepository.findAllByOrderByPointsDesc());
    }
}
