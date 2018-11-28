package service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private String position;

    @Column(name = "nation")
    private String nation;

    @Column(name = "cost")
    private int cost;

    @ManyToMany
    @Column(name = "owners")
    @JsonIgnore
    List<User> owners;

    @ManyToMany
    List<UsersTeam> teams;
}
