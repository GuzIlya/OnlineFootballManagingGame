package service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import service.forms.PlayerForm;
import service.forms.UserForm;
import service.services.PlayersService;
import service.transfer.PlayerDto;

import java.util.List;

@RestController
public class PlayersController {
    @Autowired
    private PlayersService playersService;

    @PostMapping("/getPlayersToBuy")
    public ResponseEntity<List<PlayerDto>> addUser(@RequestBody PlayerForm playerForm){
        return ResponseEntity.ok(playersService.findAllPlayersToBuy(playerForm));
    }
}
