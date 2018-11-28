package service.transfer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRateDto {
    private String login;
    private int points;


    public static UserRateDto from(User user) {
        return UserRateDto.builder()
                .login(user.getLogin())
                .points(user.getPoints())
                .build();
    }

    public static List<UserRateDto> from(List<User> users) {
        return users.stream().map(UserRateDto::from).collect(Collectors.toList());
    }
}
