package service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.forms.PlayerForm;
import service.models.Player;
import service.models.Token;
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
    public List<PlayerDto> findAllPlayersToBuy(Token token, String position, int maxCost) {
        Boolean flag = true;
        List<PlayerDto> playerDtoList = new ArrayList<>();
        List<Player> playersFounded = playersRepository.findAllByPosition(position);
        for(Player player: playersFounded){
            if(player.getCost() <= maxCost){
                for (Player playerOwned: token.getUser().getPlayers())
                    if (playerOwned.equals(player)) flag = false;

                if (flag){
                    playerDtoList.add(from(player));
                } else flag = true;
            }
        }
        if(playerDtoList.isEmpty())
            throw new IllegalArgumentException("No players found");

        return playerDtoList;
    }
}
