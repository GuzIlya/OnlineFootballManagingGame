package service.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.models.MatchResult;
import service.models.Player;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResultDto {
    private String teamAName;
    private String teamBName;
    private int teamAScore;
    private int teamBScore;


    public static MatchResultDto from(MatchResult matchResult) {
        return MatchResultDto.builder()
                .teamAName(matchResult.getTeamAName())
                .teamBName(matchResult.getTeamBName())
                .teamAScore(matchResult.getTeamAScore())
                .teamBScore(matchResult.getTeamBScore())
                .build();
    }

    public static List<MatchResultDto> from(List<MatchResult> matchResults) {
        return matchResults.stream().map(MatchResultDto::from).collect(Collectors.toList());
    }
}
