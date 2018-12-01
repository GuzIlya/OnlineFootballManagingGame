package service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.forms.PlayersResultForm;
import service.forms.ResultForm;
import service.models.MatchResult;
import service.models.Player;
import service.models.RealTeam;
import service.models.User;
import service.repositories.*;
import service.services.ResultsCalculatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResultsCalculatorServiceImpl implements ResultsCalculatorService {
    @Autowired
    private RealTeamsRepository realTeamsRepository;

    @Autowired
    private PlayersRepository playersRepository;

    @Autowired
    private TeamsRepository teamsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    @Override
    public void addResult(ResultForm resultForm) {
    }

    @Override
    public List<List<String>> getTeamPlayers(String teamA, String teamB) {
        List<List<String>> playerNames = new ArrayList<>();
        List<String> playerANames = new ArrayList<>();
        List<String> playerBNames = new ArrayList<>();
        Optional<RealTeam> teamAcandidate = realTeamsRepository.findByTeamName(teamA);
        if(teamAcandidate.isPresent())
            for (Player player : teamAcandidate.get().getPlayers())
                playerANames.add(player.getName());
        else throw new IllegalArgumentException("TeamA not found");

        Optional<RealTeam> teamBcandidate = realTeamsRepository.findByTeamName(teamB);
        if(teamBcandidate.isPresent())
            for (Player player : teamBcandidate.get().getPlayers())
                playerBNames.add(player.getName());
        else throw new IllegalArgumentException("TeamB not found");

        playerNames.add(playerANames);
        playerNames.add(playerBNames);
        return playerNames;
    }

    @Override
    public void calculateResult(PlayersResultForm playersResultForm) {

        for (int  i = 0; i < playersResultForm.getTeamAPlayers().size(); i++){
            Player player = playersRepository.findByName(playersResultForm.getTeamAPlayers().get(i));
            if (player != null){
                List<User> playerOwners = player.getOwners();
                if (playerOwners != null){
                    for (User owner: playerOwners){
                        if (owner.getTeamOwned() != null)
                            if (owner.getTeamOwned().getPlayers().contains(player)){
                                owner.setPoints(owner.getPoints() + playersResultForm.getTeamAResults().get(i));
                                owner.setBalance(owner.getBalance() + playersResultForm.getTeamAResults().get(i) / 10);
                                usersRepository.save(owner);
                            }
                    }
                }
            }
        }

        for (int  i = 0; i < playersResultForm.getTeamBPlayers().size(); i++){
            Player player = playersRepository.findByName(playersResultForm.getTeamBPlayers().get(i));
            if (player != null){
                List<User> playerOwners = player.getOwners();
                if (playerOwners != null){
                    for (User owner: playerOwners){
                        if (owner.getTeamOwned() != null)
                            if (owner.getTeamOwned().getPlayers().contains(player)){
                                owner.setPoints(owner.getPoints() + playersResultForm.getTeamBResults().get(i));
                                owner.setBalance(owner.getBalance() + playersResultForm.getTeamBResults().get(i) / 10);
                                usersRepository.save(owner);
                            }
                    }
                }
            }
        }





        MatchResult matchResult = new MatchResult();
        matchResult.setTeamAName(playersResultForm.getTeamA());
        matchResult.setTeamBName(playersResultForm.getTeamB());
        matchResult.setTeamAScore(playersResultForm.getTeamAScore());
        matchResult.setTeamBScore(playersResultForm.getTeamBScore());
        matchResultRepository.save(matchResult);
    }
}
