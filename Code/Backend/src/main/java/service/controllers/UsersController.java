package service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.models.Token;
import service.models.User;
import service.repositories.TokensRepository;
import service.services.UsersService;
import service.transfer.UserDto;

import java.util.Optional;


@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private TokensRepository tokensRepository;

    @PostMapping("/buyPlayer")
    public ResponseEntity<Object> buyPlayer(){
        return ResponseEntity.ok().build();
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

    @GetMapping("/test/{user-id}")
    public User getUser(@PathVariable("user-id") Long userId){
        return usersService.findOne(userId);
    }
}
