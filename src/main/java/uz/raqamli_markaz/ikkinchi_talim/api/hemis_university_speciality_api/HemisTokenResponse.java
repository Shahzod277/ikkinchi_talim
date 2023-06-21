package uz.raqamli_markaz.ikkinchi_talim.api.hemis_university_speciality_api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.raqamli_markaz.ikkinchi_talim.domain.TokenEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HemisTokenResponse {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private Long expires_in;
    private String scope;

    public HemisTokenResponse(TokenEntity tokenEntity) {
        this.access_token = tokenEntity.getToken();
    }
}
