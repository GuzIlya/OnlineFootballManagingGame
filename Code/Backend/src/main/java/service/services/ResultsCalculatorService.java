package service.services;

import service.forms.PlayersResultForm;
import service.forms.ResultForm;

import java.util.List;

public interface ResultsCalculatorService {
    void addResult(ResultForm resultForm);
    List<List<String>> getTeamPlayers(String teamA, String teamB);
    void calculateResult(PlayersResultForm playersResultForm);
}
