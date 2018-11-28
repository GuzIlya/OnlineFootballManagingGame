package service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.forms.PlayerForm;
import service.models.Player;
import service.repositories.PlayersRepository;
import service.services.PlayersService;
import service.transfer.PlayerDto;

import java.util.ArrayList;
import java.util.List;

import static service.transfer.PlayerDto.from;

@Service
public class PlayersServiceImpl implements PlayersService {
    @Autowired
    private PlayersRepository playersRepository;

    @Override
    public List<PlayerDto> findAllPlayersToBuy(PlayerForm playerForm) {
        List<PlayerDto> playerDtoList = new ArrayList<>();
        List<Player> playersFounded = playersRepository.findAllByPosition(playerForm.getPosition());
        for(Player player: playersFounded){
            if(player.getCost() <= playerForm.getMaxCost()){
                playerDtoList.add(from(player));
            }
        }
        if(playerDtoList.isEmpty())
            throw new IllegalArgumentException("No players found");

        return playerDtoList;
    }
}
