package service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import service.forms.PlayersResultForm;
import service.forms.ResultForm;
import service.services.ResultsCalculatorService;
import service.transfer.UserDto;
import service.transfer.UserRateDto;

import java.util.List;

@RestController
public class GameController {
    @Autowired
    private ResultsCalculatorService resultsCalculatorService;

    @GetMapping("/getRealTeamSquad")
    public ResponseEntity<List<List<String>>> addUser(@RequestParam("teamA") String teamA, @RequestParam("teamB") String teamB){
        return ResponseEntity.ok(resultsCalculatorService.getTeamPlayers(teamA, teamB));
    }

    @PostMapping("/calculateResult")
    public ResponseEntity<Object> calculateResult(@RequestBody PlayersResultForm PlayersResultForm){
        resultsCalculatorService.calculateResult(PlayersResultForm);
        return ResponseEntity.ok().build();
    }
}
