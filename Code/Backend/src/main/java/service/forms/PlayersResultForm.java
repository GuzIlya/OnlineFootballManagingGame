package service.forms;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayersResultForm {
    private String teamA;
    private String teamB;
    private int teamAScore;
    private int teamBScore;
    private List<String> teamAPlayers;
    private List<Integer> teamAResults;
    private List<String> teamBPlayers;
    private List<Integer> teamBResults;
}
