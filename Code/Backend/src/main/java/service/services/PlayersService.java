package service.services;

import service.forms.PlayerForm;
import service.transfer.PlayerDto;

import java.util.List;

public interface PlayersService {
    List<PlayerDto> findAllPlayersToBuy(PlayerForm playerForm);
}
