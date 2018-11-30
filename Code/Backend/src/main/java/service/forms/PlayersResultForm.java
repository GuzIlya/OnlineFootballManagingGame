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
    private String teamAScore;
    private String teamBScore;
    private List<String> teamAPlayers;
    private List<String> teamAResults;
    private List<String> teamBPlayers;
    private List<String> teamBResults;
}
