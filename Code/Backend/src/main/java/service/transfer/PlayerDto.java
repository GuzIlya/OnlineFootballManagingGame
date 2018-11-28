package service.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.models.Player;
import service.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDto {
    private String name;
    private int cost;


    public static PlayerDto from(Player player) {
        return PlayerDto.builder()
                .name(player.getName())
                .cost(player.getCost())
                .build();
    }

    public static List<PlayerDto> from(List<Player> players) {
        return players.stream().map(PlayerDto::from).collect(Collectors.toList());
    }
}
