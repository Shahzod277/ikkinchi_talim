package uz.raqamli_markaz.ikkinchi_talim.api.hemis_university_speciality_api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.raqamli_talim.qabul_xotm.domain.HemisTokenEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HemisTokenResponse {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private Long expires_in;
    private String scope;

    public HemisTokenResponse(HemisTokenEntity hemisTokenEntity) {
        this.access_token = hemisTokenEntity.getToken();
    }
}
