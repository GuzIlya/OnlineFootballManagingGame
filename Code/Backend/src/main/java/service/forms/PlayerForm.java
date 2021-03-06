package service.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.models.Token;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerForm {
    private String token;
    private String position;
    private int maxCost;
}
