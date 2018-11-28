package service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.forms.PlayerForm;
import service.forms.UserForm;
import service.models.Token;
import service.repositories.TokensRepository;
import service.services.PlayersService;
import service.transfer.PlayerDto;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayersController {
    @Autowired
    private PlayersService playersService;

    @Autowired
    private TokensRepository tokensRepository;

    @PostMapping("/getPlayersToBuy")
    public ResponseEntity<List<PlayerDto>> addUser(@RequestBody PlayerForm playerForm){
        Optional<Token> tokenCandidate = tokensRepository.findOneByValue(playerForm.getToken());
        if(tokenCandidate.isPresent()){
            return ResponseEntity.ok(playersService.findAllPlayersToBuy(tokenCandidate.get(), playerForm.getPosition(), playerForm.getMaxCost()));
        } else throw new IllegalArgumentException("Wrong token");
    }
}
