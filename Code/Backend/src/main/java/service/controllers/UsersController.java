package service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.forms.PlayerForm;
import service.forms.TeamForm;
import service.models.Token;
import service.models.User;
import service.repositories.TokensRepository;
import service.services.UsersService;
import service.transfer.PlayerDto;
import service.transfer.UserDto;
import service.transfer.UserRateDto;

import java.util.List;
import java.util.Optional;


@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private TokensRepository tokensRepository;

    @GetMapping("/buyPlayer")
    public ResponseEntity<Object> buyPlayer(@RequestParam("token") String tokenName, @RequestParam("name") String playerName){
        Optional<Token> tokenCandidate = tokensRepository.findOneByValue(tokenName);
        if(tokenCandidate.isPresent()){
            usersService.buyPlayer(tokenCandidate.get(), playerName);
        } else throw new IllegalArgumentException("Wrong token");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAvailablePlayers")
    public ResponseEntity<List<String>> addUser(@RequestParam("token") String tokenName, @RequestParam("position") String position){
        Optional<Token> tokenCandidate = tokensRepository.findOneByValue(tokenName);
        if(tokenCandidate.isPresent()){
            return ResponseEntity.ok(usersService.getPlayersAvailable(tokenCandidate.get(), position));
        } else throw new IllegalArgumentException("Wrong token");
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserDto> getUserInfo(@RequestParam("token") String tokenName){
        Optional<Token> tokenCandidate = tokensRepository.findOneByValue(tokenName);
        if(tokenCandidate.isPresent()){
            return ResponseEntity.ok(usersService.getUserInfoToIndexPage(tokenCandidate.get()));
        } else throw new IllegalArgumentException("Wrong token");
    }

    @GetMapping("/loginOut")
    public ResponseEntity<Object> logout(@RequestParam("token") String tokenName){
        Optional<Token> tokenCandidate = tokensRepository.findOneByValue(tokenName);
        if(tokenCandidate.isPresent()){
            tokensRepository.delete(tokenCandidate.get().getId());
            return ResponseEntity.ok().build();
        } else throw new IllegalArgumentException("Wrong token");
    }

    @PostMapping("/addTeam")
    public ResponseEntity<Object> addTeam(@RequestBody TeamForm teamForm){
        Optional<Token> tokenCandidate = tokensRepository.findOneByValue(teamForm.getToken());
        if(tokenCandidate.isPresent()){
            usersService.addTeam(tokenCandidate.get(), teamForm.getPlayersList());
            return ResponseEntity.ok().build();
        } else throw new IllegalArgumentException("Wrong token");
    }

    @GetMapping("/getUsersRate")
    public ResponseEntity<List<UserRateDto>> getUsersRate(){
        return ResponseEntity.ok(usersService.getUsersRate());
    }

    @GetMapping("/test/{user-id}")
    public User getUser(@PathVariable("user-id") Long userId){
        return usersService.findOne(userId);
    }
}
