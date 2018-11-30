package service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "results")
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_a_name")
    private String teamAName;

    @Column(name = "team_b_name")
    private String teamBName;

    @Column(name = "team_a_score")
    private int teamAScore;

    @Column(name = "team_b_score")
    private int teamBScore;
}
