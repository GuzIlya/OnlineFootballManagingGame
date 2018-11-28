package service.services;

import service.forms.PlayerForm;
import service.models.Token;
import service.transfer.PlayerDto;

import java.util.List;

public interface PlayersService {
    List<PlayerDto> findAllPlayersToBuy(Token token, String position, int maxCost);
}
