package service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.forms.PlayersResultForm;
import service.forms.ResultForm;
import service.models.Player;
import service.models.RealTeam;
import service.repositories.PlayersRepository;
import service.repositories.RealTeamsRepository;
import service.repositories.TeamsRepository;
import service.repositories.UsersRepository;
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

            }
        }
    }
}
