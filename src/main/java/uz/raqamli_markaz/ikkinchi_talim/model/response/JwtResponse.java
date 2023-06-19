package uz.raqamli_markaz.ikkinchi_talim.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {

    private String token;
    private Integer userId;
    private String username;
    private List<String> roles;

    public JwtResponse(Integer userId, String username, String token, List<String> roles) {

        this.userId = userId;
        this.username = username;
        this.token = token;
        this.roles = roles;
    }
}
